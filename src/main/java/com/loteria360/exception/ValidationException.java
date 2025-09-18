package com.loteria360.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationException extends BusinessException {
    
    private final List<String> validationErrors;
    
    public ValidationException(String message, List<String> validationErrors) {
        super(message, message);
        this.validationErrors = validationErrors;
    }
    
    public ValidationException(List<String> validationErrors) {
        super("Erro de validação", "Por favor, verifique os dados informados");
        this.validationErrors = validationErrors;
    }
}
