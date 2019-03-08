package com.song.samples.cleancode.improve;

import static com.song.samples.cleancode.improve.ArgsException.ErrorCode.OK;

/**
 * 格式化字符串异常
 *
 * @author: songzeqi
 * @Date: 2018-06-14 下午3:00
 */

public class ArgsException extends Exception {
    private char elementId = '\0';
    private String parameter = null;
    private ErrorCode errorCode = OK;

    public ArgsException() {
    }

    public ArgsException(String message) {
        super(message);
    }

    public ArgsException(String parameter, ErrorCode errorCode) {
        this.parameter = parameter;
        this.errorCode = errorCode;
    }

    public ArgsException(char elementId, String parameter, ErrorCode errorCode) {
        this.elementId = elementId;
        this.parameter = parameter;
        this.errorCode = errorCode;
    }

    public char getElementId() {
        return elementId;
    }

    public void setElementId(char elementId) {
        this.elementId = elementId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage(ErrorCode errorCode) {
        switch (errorCode) {
            case OK:
                return "OK";
            case ERROR:
                return "error";
            case INVALID_ARGUMENT_FORMAT:
                return "invalid argument format";
        }
        return "";
    }

    public enum ErrorCode {
        OK, ERROR, INVALID_ARGUMENT_FORMAT;
    }
}
