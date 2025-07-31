package emakers.backend.service;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

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

    public void emprestarLivro(Integer livroId,JwtAuthenticationToken token){

        Usuario usuario = usuarioRepository.findById(Integer.valueOf(token.getName()))
            .orElseThrow(() -> new EntityNotFoundException("Usuario com ID " + token.getName() + " não encontrado"));

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setLivro(livroRepository.findById(livroId)
            .orElseThrow(() -> new EntityNotFoundException("Livro com ID " + livroId + " não encontrado")));

        emprestimo.setUsuario(usuario);

        emprestimoRepository.save(emprestimo);
    }

    public List<Emprestimo> listarEmprestimos(){
        return emprestimoRepository.findAll();
    }

    public Emprestimo buscarPorId(Integer id) {
        return emprestimoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Empréstimo com id " + id + " não encontrado."));
            // FAZER UM GLOBAL EXCEPTION HANDLER DPS
    }

    public Emprestimo atualizarEmprestimo(Integer id, EmprestimoDto emprestimoDto) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Emprestimo com ID " + id + " não encontrado"));

        emprestimo.setLivro(livroRepository
            .findById(emprestimoDto.livroId())
            .orElseThrow(() -> new EntityNotFoundException("Livro com ID " + emprestimoDto.livroId() + " não encontrado")));

        emprestimo.setUsuario(usuarioRepository
            .findById(emprestimoDto.usuarioId())
            .orElseThrow(() -> new EntityNotFoundException("Usuario com ID " + emprestimoDto.usuarioId() + " não encontrado")));

        return emprestimoRepository.save(emprestimo);
    }

    public void deletarEmprestimo(Integer id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Empréstimo com ID " + id + " não encontrado"));

        emprestimoRepository.delete(emprestimo);
    }
    

}
