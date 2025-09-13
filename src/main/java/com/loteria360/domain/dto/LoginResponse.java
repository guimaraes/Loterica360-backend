package com.loteria360.domain.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        String tipo,
        Long expiraEm
) {}
