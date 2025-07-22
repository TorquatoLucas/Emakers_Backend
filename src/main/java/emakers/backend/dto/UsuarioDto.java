package emakers.backend.dto;


public record UsuarioDto(

    int id, 
    
    String cpf,

    String cep, 

    String nome,  

    String email, 

    String senha
    
    ) {
}
