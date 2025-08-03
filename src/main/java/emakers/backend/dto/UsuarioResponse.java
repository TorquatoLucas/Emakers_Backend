package emakers.backend.dto;

import emakers.backend.viacep.Endereco;

public record UsuarioResponse(
    
    String cpf,

    Endereco endereco, 

    String nome,  

    String email, 

    String senha
    
    ) {

}
