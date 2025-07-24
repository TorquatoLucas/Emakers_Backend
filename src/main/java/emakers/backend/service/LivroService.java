package emakers.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import emakers.backend.dto.LivroDto;
import emakers.backend.mapper.LivroMapper;
import emakers.backend.model.Livro;
import emakers.backend.repository.LivroRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LivroService {

    private final LivroRepository livroRepository;

    private final LivroMapper livroMapper;

    public Boolean salvarLivro(LivroDto livroDto) {

        Livro livro = livroMapper.toLivro(livroDto);
        livroRepository.save(livro);
        return true;

    }

    public List<Livro> listaLivros() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(int id) {
        var livro = livroRepository.findById(id);

        if(livro.isPresent()){
            return livro.get();
        }

        return null;
    }

    public Livro atualizarLivro(Integer id, LivroDto livroDto) {
        Livro livro = livroRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Livro com ID " + id + " não encontrado"));

        livro.setAutor( livroDto.autor() );
        livro.setData( livroDto.data() );
        livro.setNome( livroDto.nome() );

        return livroRepository.save(livro);
    }

    public boolean deletarLivro(int id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
