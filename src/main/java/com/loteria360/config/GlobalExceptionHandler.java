package com.loteria360.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import jakarta.validation.ConstraintViolationException;
import com.loteria360.exception.BusinessException;
import com.loteria360.exception.ResourceNotFoundException;
import com.loteria360.exception.ValidationException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.warn("Argumento inválido: {}", ex.getMessage());
        
        String detail = ex.getMessage();
        // Melhorar mensagens específicas
        if (detail.contains("Email já está em uso")) {
            detail = "Este email já está cadastrado no sistema";
        } else if (detail.contains("já existe")) {
            detail = "Este registro já existe no sistema";
        } else if (detail.contains("não encontrado")) {
            detail = "O registro solicitado não foi encontrado";
        } else if (detail.contains("inválido") || detail.contains("inválida")) {
            detail = "Os dados fornecidos são inválidos";
        }
        
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/invalid-argument")
                        .title("Dados Inválidos")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail(detail)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Erro de validação: {}", ex.getMessage());
        
        List<ValidationError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String friendlyMessage = getFriendlyValidationMessage(error.getDefaultMessage(), error.getField());
            errors.add(ValidationError.builder()
                    .field(error.getField())
                    .message(friendlyMessage)
                    .rejectedValue(error.getRejectedValue())
                    .build());
        });

        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/validation-failed")
                        .title("Dados Inválidos")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail("Por favor, verifique os dados informados")
                        .errors(errors)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    private String getFriendlyValidationMessage(String message, String field) {
        if (message.contains("não deve estar vazio") || message.contains("não pode estar vazio")) {
            return getFieldName(field) + " é obrigatório";
        } else if (message.contains("deve ser um endereço de e-mail válido")) {
            return "Email deve ter um formato válido";
        } else if (message.contains("deve ter pelo menos")) {
            return getFieldName(field) + " deve ter pelo menos " + extractMinLength(message) + " caracteres";
        } else if (message.contains("deve ter no máximo")) {
            return getFieldName(field) + " deve ter no máximo " + extractMaxLength(message) + " caracteres";
        } else if (message.contains("deve ser um número")) {
            return getFieldName(field) + " deve ser um número válido";
        } else if (message.contains("deve ser positivo")) {
            return getFieldName(field) + " deve ser um valor positivo";
        } else if (message.contains("deve ser maior que")) {
            return getFieldName(field) + " deve ser maior que " + extractMinValue(message);
        } else if (message.contains("deve ser menor que")) {
            return getFieldName(field) + " deve ser menor que " + extractMaxValue(message);
        }
        return message;
    }

    private String getFieldName(String field) {
        switch (field) {
            case "nome": return "Nome";
            case "email": return "Email";
            case "senha": return "Senha";
            case "papel": return "Perfil";
            case "ativo": return "Status";
            case "preco": return "Preço";
            case "descricao": return "Descrição";
            case "cpf": return "CPF";
            case "telefone": return "Telefone";
            case "quantidade": return "Quantidade";
            case "valor": return "Valor";
            default: return field.substring(0, 1).toUpperCase() + field.substring(1);
        }
    }

    private String extractMinLength(String message) {
        try {
            return message.replaceAll(".*deve ter pelo menos (\\d+).*", "$1");
        } catch (Exception e) {
            return "3";
        }
    }

    private String extractMaxLength(String message) {
        try {
            return message.replaceAll(".*deve ter no máximo (\\d+).*", "$1");
        } catch (Exception e) {
            return "255";
        }
    }

    private String extractMinValue(String message) {
        try {
            return message.replaceAll(".*deve ser maior que (\\d+).*", "$1");
        } catch (Exception e) {
            return "0";
        }
    }

    private String extractMaxValue(String message) {
        try {
            return message.replaceAll(".*deve ser menor que (\\d+).*", "$1");
        } catch (Exception e) {
            return "999999";
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        log.warn("Credenciais inválidas: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/invalid-credentials")
                        .title("Credenciais Inválidas")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .detail("Email ou senha incorretos")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Acesso negado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/access-denied")
                        .title("Acesso Negado")
                        .status(HttpStatus.FORBIDDEN.value())
                        .detail("Você não tem permissão para acessar este recurso")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException ex) {
        log.warn("Usuário desabilitado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/account-disabled")
                        .title("Conta Desabilitada")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .detail("Sua conta está desabilitada. Entre em contato com o administrador")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse> handleLockedException(LockedException ex) {
        log.warn("Usuário bloqueado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/account-locked")
                        .title("Conta Bloqueada")
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .detail("Sua conta está bloqueada. Entre em contato com o administrador")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.warn("Violação de integridade de dados: {}", ex.getMessage());
        
        String detail = "Erro de integridade de dados";
        if (ex.getMessage().contains("duplicate") || ex.getMessage().contains("Duplicate")) {
            detail = "Este registro já existe no sistema";
        } else if (ex.getMessage().contains("foreign key") || ex.getMessage().contains("FK")) {
            detail = "Não é possível excluir este registro pois ele está sendo utilizado";
        } else if (ex.getMessage().contains("constraint") || ex.getMessage().contains("unique")) {
            detail = "Dados duplicados não são permitidos";
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/data-integrity-violation")
                        .title("Erro de Integridade de Dados")
                        .status(HttpStatus.CONFLICT.value())
                        .detail(detail)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.warn("Registro não encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/not-found")
                        .title("Registro Não Encontrado")
                        .status(HttpStatus.NOT_FOUND.value())
                        .detail("O registro solicitado não foi encontrado")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.warn("Parâmetro obrigatório ausente: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/missing-parameter")
                        .title("Parâmetro Obrigatório Ausente")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail("O parâmetro '" + ex.getParameterName() + "' é obrigatório")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.warn("Tipo de parâmetro inválido: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/invalid-parameter-type")
                        .title("Tipo de Parâmetro Inválido")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail("O parâmetro '" + ex.getName() + "' deve ser do tipo " + 
                            (ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "válido"))
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.warn("Mensagem HTTP não legível: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/malformed-request")
                        .title("Requisição Malformada")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail("Os dados enviados estão em formato inválido")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        log.warn("Violação de constraint: {}", ex.getMessage());
        
        List<ValidationError> errors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> 
            errors.add(ValidationError.builder()
                    .field(violation.getPropertyPath().toString())
                    .message(violation.getMessage())
                    .rejectedValue(violation.getInvalidValue())
                    .build())
        );

        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/constraint-violation")
                        .title("Violação de Restrições")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail("Os dados fornecidos violam as regras de negócio")
                        .errors(errors)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.warn("Erro de negócio: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/business-error")
                        .title("Erro de Negócio")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail(ex.getUserMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.warn("Recurso não encontrado: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/resource-not-found")
                        .title("Recurso Não Encontrado")
                        .status(HttpStatus.NOT_FOUND.value())
                        .detail(ex.getUserMessage())
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(ValidationException ex) {
        log.warn("Erro de validação customizada: {}", ex.getMessage());
        
        List<ValidationError> errors = new ArrayList<>();
        ex.getValidationErrors().forEach(errorMessage -> 
            errors.add(ValidationError.builder()
                    .field("")
                    .message(errorMessage)
                    .rejectedValue(null)
                    .build())
        );

        return ResponseEntity.badRequest()
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/validation-error")
                        .title("Erro de Validação")
                        .status(HttpStatus.BAD_REQUEST.value())
                        .detail(ex.getUserMessage())
                        .errors(errors)
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Erro interno do servidor: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .type("https://loteria360.com/errors/internal-server-error")
                        .title("Erro Interno do Servidor")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .detail("Ocorreu um erro inesperado. Tente novamente mais tarde.")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ErrorResponse {
        private String type;
        private String title;
        private int status;
        private String detail;
        private List<ValidationError> errors;
        
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime timestamp;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {
        private String field;
        private String message;
        private Object rejectedValue;
    }
}
