package com.loteria360.config;

import com.loteria360.security.JwtAuthFilter;
import com.loteria360.security.UsuarioDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.loteria360.security.CurrentUserArgumentResolver;

import java.util.Arrays;
import java.util.List;
import org.springframework.lang.NonNull;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtAuthFilter jwtAuthFilter;
    private final UsuarioDetailsService usuarioDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                .requestMatchers("/api/v1/caixas/ativas", "/api/v1/caixas/*/toggle-status").permitAll()
                
                // Endpoints apenas para ADMIN
                .requestMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/jogos/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers("/api/v1/boloes/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers("/api/v1/caixas/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers("/api/v1/turnos/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers("/api/v1/movimentos/**").hasAnyRole("ADMIN", "GERENTE")
                .requestMatchers("/api/v1/relatorios/**").hasAnyRole("ADMIN", "GERENTE", "AUDITOR")
                .requestMatchers("/api/v1/dashboard/**").hasAnyRole("ADMIN", "GERENTE", "AUDITOR")
                
                // Endpoints para VENDEDOR - apenas vendas e clientes (necessário para vendas)
                .requestMatchers("/api/v1/vendas/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
                .requestMatchers("/api/v1/vendas-caixa/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
                .requestMatchers("/api/v1/contagem-caixa/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
                .requestMatchers("/api/v1/clientes/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
                
                // Endpoints de leitura para VENDEDOR (necessários para vendas)
                .requestMatchers("/api/v1/jogos/ativos").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
                .requestMatchers("/api/v1/caixas/ativas").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
                
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(usuarioDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver());
    }
}
