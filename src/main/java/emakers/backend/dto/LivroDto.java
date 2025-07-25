package emakers.backend.dto;

import java.time.LocalDate;

public record LivroDto(
    String nome,
    String autor,
    LocalDate data
) {}
