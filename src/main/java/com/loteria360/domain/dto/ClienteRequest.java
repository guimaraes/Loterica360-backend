package com.loteria360.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ClienteRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;
    
    @Pattern(regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{11})$", message = "CPF deve ter 11 dígitos ou formato XXX.XXX.XXX-XX")
    private String cpf;
    
    @Pattern(regexp = "^(\\(\\d{2}\\)\\s\\d{5}-\\d{4}|\\d{10,11})$", message = "Telefone deve ter formato válido")
    private String telefone;
    
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    @NotNull(message = "Consentimento LGPD é obrigatório")
    private Boolean consentimentoLgpd;
}
