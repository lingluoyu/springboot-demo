package com.lingluoyu.excel;

import com.alibaba.excel.context.AnalysisContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static com.lingluoyu.excel.ExceptionUtils.newException;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:29
 */
@Slf4j
public abstract class ExcelReadProcessor {
    private static final Integer BATCH_UPLOAD_SIZE = 1000;

    private static final Integer MIN_SIZE = 2;

    private ExcelImportContext<T> excelContext;

    private List<Validator> validators = Lists.newArrayList();

    private final List<ExcelRowWrapper<T>> rowWrappers = Lists.newArrayList();

    @Value("${excel.import.maxRowSize:30000}")
    private Integer excelImportMaxRowSize;


    @Autowired(required = false)
    public void setValidators(List<Validator> validators) {
        this.validators = validators;
    }

    public List<Validator> getValidators() {
        return this.validators;
    }

    public List<ExcelRowWrapper<T>> getRowWrappers() {
        return rowWrappers;
    }

    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context, CompletableFuture<Throwable> future) {
        try {
            globalCheck(headMap, context);
        } catch (Throwable e) {
            future.complete(e);
            throw e;
        }
        future.complete(null);
        long start = System.currentTimeMillis();
        log.info("Analysis init start");
        init(headMap);
        log.info("Analysis init end time spend: {}ms", System.currentTimeMillis() - start);
    }

    private void globalCheck(Map<Integer, String> headMap, AnalysisContext context) {
        Integer total = context.readSheetHolder().getApproximateTotalRowNumber();
        if (total > excelImportMaxRowSize) {
            throw newException("导入数据量超出上限");
        }

        if (total < MIN_SIZE) {
            throw newException("导入数据不能为空");
        }
        ExcelTemplate<T> template = excelContext.getTemplate();
        List<String> headerNames = template.getHeaderNames(excelContext.getParam("excelType"));

        if (headMap.size() != headerNames.size()) {
            throw newException("请使用正确模板上传");
        }

        headMap.forEach((index, name) -> {
            if (!headerNames.contains(name)) {
                log.error("导入表头不正确：{}", name);
                throw newException("请使用正确模板上传");
            }
        });

        if (new HashSet<>(headMap.values()).size() != headMap.size()) {
            throw newException("文件中存在相同名称的列");
        }
    }

    public ValidateResult validateOne(Map<Integer, String> data, Integer rowIndex) {
        List<Validator> validators = getValidators();
        return validators.stream().map(v -> v.test(data, excelContext, rowIndex))
                .filter(Objects::nonNull).reduce((v1, v2) -> {
                    if (UploadDataStatus.FAIL.equals(v2.getStatus())) {
                        v1.addError(v2.getMessage());
                    }
                    return v1;
                }).orElseGet(ValidateResult::new);
    }

    public ExcelImportContext<T> getExcelContext() {
        return excelContext;
    }

    public void setExcelContext(ExcelImportContext<T> excelContext) {
        this.excelContext = excelContext;
    }

    public void process(ExcelRowWrapper<T> rowWrapper) {
        populateCommonField(rowWrapper);
        rowWrappers.add(rowWrapper);
        if (rowWrappers.size() >= BATCH_UPLOAD_SIZE) {
            processBatch();
            rowWrappers.clear();
        }
    }

    public void finish() {
        log.info("excel数据读取end");
        // 余量数据
        processBatch();
        rowWrappers.clear();
        // 后置处理
        postProcessFinish();
    }

    /**
     * 初始化操作
     *
     * @param headMap 表头Map
     */
    protected abstract void init(Map<Integer, String> headMap);

    /**
     * 填充额外字段公共数据
     *
     * @param rowWrapper
     */
    protected abstract void populateCommonField(ExcelRowWrapper<T> rowWrapper);

    /**
     * 分批处理数据 note: processBatch后 #rowWrappers会被清空
     */
    protected abstract void processBatch();

    /**
     * 后置处理完成
     */
    protected abstract void postProcessFinish();
}
