package com.lingluoyu.excel;

import org.springframework.core.Ordered;

import java.util.Map;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:34
 */
public interface Validator extends Ordered {
    <T> ValidateResult test(Map<Integer, String> data, ExcelImportContext<T> excelContext, Integer rowIndex);
}
