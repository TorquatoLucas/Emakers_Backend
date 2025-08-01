package emakers.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emakers.backend.dto.UsuarioDto;
import emakers.backend.model.Usuario;
import emakers.backend.service.UsuarioService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> salvarUsuario(@RequestBody UsuarioDto usuarioDto){
        
        if(usuarioService.salvarUsuario(usuarioDto)){
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
        
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')") // FAZER VERIFICAÇÃO DPS PARA O PROPRIO USUARIO CONSEGUIR
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDto UsuarioDto) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, UsuarioDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')") // FAZER VERIFICAÇÃO DPS PARA O PROPRIO USUARIO CONSEGUIR
    public ResponseEntity<Void> deletarUsuario(@PathVariable Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}
