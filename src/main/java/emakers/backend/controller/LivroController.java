package emakers.backend.controller;

import emakers.backend.dto.LivroDto;
import emakers.backend.model.Livro;
import emakers.backend.service.LivroService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/livro")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Livro", description = "Operações relacionadas a livros")
public class LivroController {

    private final LivroService livroService;

    @Operation(summary = "Salva um novo livro (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Livro salvo com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
    })
    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Livro> salvarLivro(
            @RequestBody LivroDto livroDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(livroService.salvarLivro(livroDto));
    }

    @Operation(summary = "Lista todos os livros disponíveis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
    })
    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listaLivros();
        return ResponseEntity.ok(livros);
    }

    @Operation(summary = "Busca um livro por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro encontrado"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(
            @Parameter(description = "ID do livro") @PathVariable Integer id) {
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @Operation(summary = "Atualiza um livro existente (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Livro> atualizarLivro(
            @Parameter(description = "ID do livro") @PathVariable Integer id,
            @RequestBody LivroDto livroDto) {
        Livro atualizado = livroService.atualizarLivro(id, livroDto);
        return ResponseEntity.ok(atualizado);
    }

    @Operation(summary = "Deleta um livro por ID (ADM)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deletarLivro(
            @Parameter(description = "ID do livro") @PathVariable Integer id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
