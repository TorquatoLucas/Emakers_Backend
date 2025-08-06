package emakers.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emakers.backend.dto.EmprestimoDto;
import emakers.backend.model.Emprestimo;
import emakers.backend.service.EmprestimoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/emprestimo")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Empréstimo", description = "Operações relacionadas a empréstimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    @Operation(summary = "Realiza o empréstimo de um livro para o usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro emprestado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Livro ou Usuário não encontrado")
    })
    @PostMapping("/livro/{livroId}")
    public ResponseEntity<Void> emprestarLivro(
            @Parameter(description = "ID do livro a ser emprestado") @PathVariable Integer livroId,
            JwtAuthenticationToken token) {
        emprestimoService.emprestarLivro(livroId, token);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Lista todos os empréstimos (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de empréstimos retornada com sucesso")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    @Operation(summary = "Busca um empréstimo por ID (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empréstimo encontrado"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Emprestimo> buscarPorId(
            @Parameter(description = "ID do empréstimo") @PathVariable Integer id) {
        Emprestimo emprestimo = emprestimoService.buscarPorId(id);
        return ResponseEntity.ok(emprestimo);
    }

    @Operation(summary = "Atualiza um empréstimo existente (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empréstimo atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Emprestimo> atualizarEmprestimo(
            @Parameter(description = "ID do empréstimo") @PathVariable Integer id,
            @RequestBody EmprestimoDto emprestimoDto) {
        Emprestimo atualizado = emprestimoService.atualizarEmprestimo(id, emprestimoDto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deleta um empréstimo por ID (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Empréstimo deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empréstimo não encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Void> deletarEmprestimo(
            @Parameter(description = "ID do empréstimo") @PathVariable Integer id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Realiza a devolução de um livro emprestado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro devolvido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Empréstimo ou livro não encontrado")
    })
    @PatchMapping("/devolver/livro/{livroId}")
    public ResponseEntity<Void> devolverLivro(
            @Parameter(description = "ID do livro a ser devolvido") @PathVariable Integer livroId,
            JwtAuthenticationToken token) {
        emprestimoService.devolverLivro(livroId, token);
        return ResponseEntity.ok().build();
    }

}
