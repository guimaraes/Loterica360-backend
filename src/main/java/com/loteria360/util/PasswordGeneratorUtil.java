package com.loteria360.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utilitário para gerar hashes BCrypt de senhas.
 * Usa a mesma configuração do Spring Security da aplicação.
 */
@Component
public class PasswordGeneratorUtil {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordGeneratorUtil() {
        // Usa a mesma configuração padrão do BCryptPasswordEncoder
        // que está configurado no SecurityConfig
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Gera um hash BCrypt para a senha fornecida.
     * 
     * @param senha A senha em texto plano
     * @return O hash BCrypt da senha
     */
    public String generateHash(String senha) {
        return passwordEncoder.encode(senha);
    }

    /**
     * Verifica se uma senha em texto plano corresponde ao hash BCrypt.
     * 
     * @param senha A senha em texto plano
     * @param hash O hash BCrypt armazenado
     * @return true se a senha corresponder ao hash, false caso contrário
     */
    public boolean matches(String senha, String hash) {
        return passwordEncoder.matches(senha, hash);
    }

    /**
     * Método principal para testar a geração de hash.
     * Pode ser executado diretamente para gerar hashes para migrações.
     */
    public static void main(String[] args) {
        PasswordGeneratorUtil generator = new PasswordGeneratorUtil();
        
        // Se um argumento for fornecido, usar como senha
        String senha = args.length > 0 ? args[0] : "123456";
        
        String hash = generator.generateHash(senha);
        
        System.out.println("Senha: " + senha);
        System.out.println("Hash BCrypt: " + hash);
        
        // Verificar se o hash funciona
        boolean isValid = generator.matches(senha, hash);
        System.out.println("Hash válido: " + isValid);
    }
}
