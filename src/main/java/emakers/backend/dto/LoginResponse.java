package emakers.backend.dto;

public record LoginResponse(
    String tokenAcesso,
    long expiraEm
) {}
