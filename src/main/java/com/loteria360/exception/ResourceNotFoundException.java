package com.loteria360.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends BusinessException {
    
    private final String resourceType;
    private final String resourceId;
    
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(
            String.format("%s com ID '%s' não encontrado", resourceType, resourceId),
            String.format("%s não encontrado", resourceType)
        );
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
    
    public ResourceNotFoundException(String resourceType, String resourceId, String field) {
        super(
            String.format("%s com %s '%s' não encontrado", resourceType, field, resourceId),
            String.format("%s não encontrado", resourceType)
        );
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}
