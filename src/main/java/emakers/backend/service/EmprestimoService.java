package emakers.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.dto.EmprestimoDto;
import emakers.backend.exception.IdNotFoundException;
import emakers.backend.model.Emprestimo;
import emakers.backend.model.Usuario;
import emakers.backend.repository.EmprestimoRepository;
import emakers.backend.repository.LivroRepository;
import emakers.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmprestimoService {
    
    private final LivroRepository livroRepository;

    private final UsuarioRepository usuarioRepository;

    private final EmprestimoRepository emprestimoRepository;

    @Transactional
    public void emprestarLivro(Integer livroId,JwtAuthenticationToken token){

        Usuario usuario = usuarioRepository.findById(Integer.valueOf(token.getName()))
            .orElseThrow(IdNotFoundException::new);

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setLivro(livroRepository.findById(livroId)
            .orElseThrow(IdNotFoundException::new));

        emprestimo.setUsuario(usuario);

        emprestimo.setPrazoDevolucao(LocalDate.now().plusDays(30));

        emprestimoRepository.save(emprestimo);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarEmprestimos(){
        return emprestimoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Emprestimo buscarPorId(Integer id) {
        return emprestimoRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);
    }

    @Transactional
    public Emprestimo atualizarEmprestimo(Integer id, EmprestimoDto emprestimoDto) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);

        emprestimo.setLivro(livroRepository
            .findById(emprestimoDto.livroId())
            .orElseThrow(IdNotFoundException::new));

        emprestimo.setUsuario(usuarioRepository
            .findById(emprestimoDto.usuarioId())
            .orElseThrow(IdNotFoundException::new));

        emprestimo.setDataDevolvido(emprestimoDto.dataDevolvido());
        emprestimo.setDevolvido(emprestimoDto.devolvido());
        emprestimo.setPrazoDevolucao(emprestimoDto.prazoDevolucao());

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public void deletarEmprestimo(Integer id) {
        if (emprestimoRepository.existsById(id)) {
            emprestimoRepository.deleteById(id);
        }else{
            throw new IdNotFoundException();
        }
    }

    @Transactional
    public void devolverLivro(Integer livroId, JwtAuthenticationToken token) {
        Integer usuarioId = Integer.valueOf(token.getName());

        Emprestimo emprestimo = emprestimoRepository.findByLivroIdAndUsuarioIdAndDevolvidoFalse(livroId, usuarioId)
            .orElseThrow(IdNotFoundException::new);

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolvido(LocalDate.now());
        
        emprestimoRepository.save(emprestimo);
    }

    
}
