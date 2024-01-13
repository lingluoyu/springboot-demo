package com.lingluoyu.excel;

import lombok.Data;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:35
 */
@Data
public class ValidateResult {
    private Integer rowIndex;
    private String message;
    private String typeMessage = "校验错误";
    private UploadDataStatus status = UploadDataStatus.SUCCESS;

    public void addError(String message) {
        if (this.message != null) {
            this.message = this.message + ";" + message;
        } else {
            this.message = message;
            status = UploadDataStatus.FAIL;
        }
    }
}
