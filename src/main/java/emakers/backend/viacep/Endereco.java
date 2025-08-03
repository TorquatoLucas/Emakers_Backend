package emakers.backend.viacep;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Endereco(

    String cep,

    String logradouro,

    String bairro,

    String localidade,

    String uf

) {}