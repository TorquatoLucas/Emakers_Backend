package emakers.backend.mapper;

import org.mapstruct.Mapper;

import emakers.backend.dto.LivroDto;
import emakers.backend.model.Livro;


@Mapper (componentModel = "spring")
public interface LivroMapper {

    
    LivroDto tDto(Livro livro);
    Livro toLivro(LivroDto livroDto);

}
