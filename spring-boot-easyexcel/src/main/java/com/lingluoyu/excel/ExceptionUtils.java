package com.lingluoyu.excel;

/**
 * @Author : lingluoyu
 * @create 2024/1/13 12:50
 */
public class ExceptionUtils {
    public static DefaultException newException(String message) {
        return new DefaultException(message);
    }
}
