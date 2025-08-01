package emakers.backend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.dto.EmprestimoDto;
import emakers.backend.model.Emprestimo;
import emakers.backend.model.Usuario;
import emakers.backend.repository.EmprestimoRepository;
import emakers.backend.repository.LivroRepository;
import emakers.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
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
            .orElseThrow(() -> new EntityNotFoundException("Usuario com ID " + token.getName() + " não encontrado"));

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setLivro(livroRepository.findById(livroId)
            .orElseThrow(() -> new EntityNotFoundException("Livro com ID " + livroId + " não encontrado")));

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
            .orElseThrow(() -> new EntityNotFoundException("Empréstimo com id " + id + " não encontrado."));
            // FAZER UM GLOBAL EXCEPTION HANDLER DPS
    }

    @Transactional
    public Emprestimo atualizarEmprestimo(Integer id, EmprestimoDto emprestimoDto) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Emprestimo com ID " + id + " não encontrado"));

        emprestimo.setLivro(livroRepository
            .findById(emprestimoDto.livroId())
            .orElseThrow(() -> new EntityNotFoundException("Livro com ID " + emprestimoDto.livroId() + " não encontrado")));

        emprestimo.setUsuario(usuarioRepository
            .findById(emprestimoDto.usuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuario com ID " + emprestimoDto.usuarioId() + " não encontrado")));

        emprestimo.setDataDevolvido(emprestimoDto.dataDevolvido());
        emprestimo.setDevolvido(emprestimoDto.devolvido());
        emprestimo.setPrazoDevolucao(emprestimoDto.prazoDevolucao());

        return emprestimoRepository.save(emprestimo);
    }

    @Transactional
    public boolean deletarEmprestimo(Integer id) {
        if (emprestimoRepository.existsById(id)) {
            emprestimoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void devolverLivro(Integer livroId, JwtAuthenticationToken token) {
        Integer usuarioId = Integer.valueOf(token.getName());

        Emprestimo emprestimo = emprestimoRepository.findByLivroIdAndUsuarioIdAndDevolvidoFalse(livroId, usuarioId)
            .orElseThrow(() -> new EntityNotFoundException("Empréstimo não encontrado para este livro e usuário."));

        emprestimo.setDevolvido(true);
        emprestimo.setDataDevolvido(LocalDate.now());
        emprestimoRepository.save(emprestimo);
    }

    
}
