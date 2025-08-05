package emakers.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.dto.LivroDto;
import emakers.backend.exception.IdNotFoundException;
import emakers.backend.mapper.LivroMapper;
import emakers.backend.model.Livro;
import emakers.backend.repository.LivroRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    private final LivroMapper livroMapper;

    @Transactional
    public Livro salvarLivro(LivroDto livroDto) {
        Livro livro = livroMapper.toLivro(livroDto);
        return livroRepository.save(livro);
    }

    @Transactional(readOnly = true)
    public List<Livro> listaLivros() {
        return livroRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Integer id) {
        return livroRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);
    }

    @Transactional
    public Livro atualizarLivro(Integer id, LivroDto livroDto) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);

        livro.setAutor( livroDto.autor() );
        livro.setData( livroDto.data() );
        livro.setNome( livroDto.nome() );

        return livroRepository.save(livro);
    }

    @Transactional
    public void deletarLivro(Integer id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
        }else{
            throw new IdNotFoundException();
        }
    }
}
