package com.lingluoyu.excel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:30
 */
@Data
public class ExcelImportContext<T> {
    private String fileId = UUID.randomUUID().toString()
            .replaceAll("-", "");

    @JsonIgnore
    private List<String> sortKeys;

    @JsonIgnore
    private ExcelTemplate<T> template;

    private Map<String, Object> params;

    private Map<Integer, String> headMap;

    @JsonIgnore
    private Map<Integer, Field> fieldMap;

    @JsonIgnore
    private Boolean globalCheckFail = false;

    @JsonIgnore
    private MultipartFile file;

    public ExcelImportContext() {}

    public ExcelImportContext(ExcelTemplate<T> template) {
        this.template = template;
    }

    public ExcelImportContext(ExcelTemplate<T> template, String tenantKey, Map<String, Object> params) {
        this(template);
        this.fileId = tenantKey + "_" + this.fileId;
        this.params = params;
    }

    public void setHeadMap(Map<Integer, String> headMap) {
        this.headMap = headMap;
    }

    public void setFieldMap(Map<Integer, Field> fieldMap) {
        this.fieldMap = fieldMap;
    }


    public <T> T getParam(String key) {
        return (T) params.get(key);
    }

    @Override
    public String toString() {
        return "ExcelImportContext{" +
                "fileId='" + fileId + '\'' +
                ", sortKeys=" + sortKeys +
                ", params=" + params +
                ", headMap=" + headMap +
                ", fieldMap=" + fieldMap +
                ", globalCheckFail=" + globalCheckFail +
                '}';
    }
}
