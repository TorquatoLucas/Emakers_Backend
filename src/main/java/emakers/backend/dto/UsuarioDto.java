package emakers.backend.dto;


public record UsuarioDto(
    
    String cpf,

    String cep, 

    String nome,  

    String email, 

    String senha
    
    ) {
}
