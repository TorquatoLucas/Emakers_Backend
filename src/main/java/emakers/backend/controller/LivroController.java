package emakers.backend.controller;

import emakers.backend.dto.LivroDto;
import emakers.backend.model.Livro;
import emakers.backend.service.LivroService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/livro")
public class LivroController {

    private final LivroService livroService;

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<String> salvarLivro(@RequestBody LivroDto livroDto) {
        
        if(livroService.salvarLivro(livroDto)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Livro criado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Livro já existe");

    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listaLivros();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Integer id) {
        Livro livro = livroService.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Integer id, @RequestBody LivroDto livroDto) {
        Livro atualizado = livroService.atualizarLivro(id, livroDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<Void> deletarLivro(@PathVariable Integer id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}
