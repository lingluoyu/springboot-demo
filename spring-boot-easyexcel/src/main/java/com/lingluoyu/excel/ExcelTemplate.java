package com.lingluoyu.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import jakarta.servlet.http.HttpServletResponse;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:31
 */
public interface ExcelTemplate<T> {
    String getTemplateName();

    Class<T> getImportHeader();

    default Stream<Field> getHeaderFieldStream() {
        return Arrays.stream(getImportHeader().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(DefaultExcelProperty.class));
    }

    default List<String> getHeaderNames(Object param) {
        return getHeaderFieldStream().map(field -> {
                    DefaultExcelProperty anno = field.getAnnotation(DefaultExcelProperty.class);
                    return anno.value();
                })
                .collect(Collectors.toList());
    }


    /**
     * 导出数据
     * @param response
     * @param params
     */
    void export(HttpServletResponse response, Map<String, Object> params);
}
