package com.loteria360.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final String userMessage;
    
    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
        this.userMessage = message;
    }
    
    public BusinessException(String message, String userMessage) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
        this.userMessage = userMessage;
    }
    
    public BusinessException(String errorCode, String message, String userMessage) {
        super(message);
        this.errorCode = errorCode;
        this.userMessage = userMessage;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "BUSINESS_ERROR";
        this.userMessage = message;
    }
}
