package emakers.backend.dto;

import java.time.LocalDate;

public record EmprestimoDto(

    Integer livroId,

    Integer usuarioId,

    LocalDate dataDevolvido,

    LocalDate prazoDevolucao,

    Boolean devolvido
    
) {
    
}
