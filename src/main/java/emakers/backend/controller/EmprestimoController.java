package emakers.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emakers.backend.dto.EmprestimoDto;
import emakers.backend.model.Emprestimo;
import emakers.backend.service.EmprestimoService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/emprestimo")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @PostMapping("/{livroId}")
    public ResponseEntity<Void> emprestarLivro(@PathVariable Integer livroId, JwtAuthenticationToken token) {

        emprestimoService.emprestarLivro(livroId, token);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Integer id) {
        Emprestimo Emprestimo = emprestimoService.buscarPorId(id);
        return ResponseEntity.ok(Emprestimo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Emprestimo> atualizarEmprestimo(@PathVariable Integer id, @RequestBody EmprestimoDto EmprestimoDto) {
        Emprestimo atualizado = emprestimoService.atualizarEmprestimo(id, EmprestimoDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Integer id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }
}
