package com.lingluoyu.excel;

import java.util.Map;
import java.util.Optional;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:43
 */
public class ExcelRowWrapper<T> {
    private String id;
    private Integer row;
    private T data;
    private ValidateResult result;
    private Map<String, Integer> fieldIndex;

    private int sortKey;

    public ExcelRowWrapper<T> data(T data) {
        this.data = data;
        return this;
    }

    public ExcelRowWrapper<T> row(Integer row) {
        this.row = row;
        return this;
    }

    public ExcelRowWrapper<T> result(ValidateResult result) {
        this.result = result;
        return this;
    }

    public ExcelRowWrapper<T> fieldIndex(Map<String, Integer> map) {
        this.fieldIndex = map;
        return this;
    }

    public void addCommonError(String message) {
        result.addError((row + 1) + "行:" + message);
    }

    public void addCommonError(String fieldName, String message) {
        Optional.ofNullable(fieldIndex.get(fieldName))
                .ifPresent(index -> {
                    result.addError((row + 1) + "行" + (index + 1) + "列:" + message);
                });
    }
}
