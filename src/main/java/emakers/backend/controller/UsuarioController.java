package emakers.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import emakers.backend.dto.UsuarioDto;
import emakers.backend.dto.UsuarioResponse;
import emakers.backend.model.Usuario;
import emakers.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
@Tag(name = "Usuário", description = "Operações relacionadas a usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(
        summary = "Cadastrar novo usuário",
        description = "Cria um novo usuário no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Usuário inválido"),
        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody UsuarioDto usuarioDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuarioDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    @Operation(
        summary = "Buscar usuário por ID (ADM)",
        description = "Retorna os dados de um usuário específico",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UsuarioResponse> buscarPorId(
        @Parameter(description = "ID do usuário") @PathVariable Integer id
    ) {
        UsuarioResponse usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    @Operation(
        summary = "Listar todos os usuários (ADM)",
        description = "Retorna uma lista com todos os usuários cadastrados",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    @Operation(
        summary = "Atualizar usuário (ADM)",
        description = "Atualiza os dados de um usuário específico",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Usuario> atualizarUsuario(
        @Parameter(description = "ID do usuário a ser atualizado") @PathVariable Integer id,
        @RequestBody UsuarioDto UsuarioDto
    ) {
        Usuario atualizado = usuarioService.atualizarUsuario(id, UsuarioDto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADM')")
    @Operation(
        summary = "Deletar usuário (ADM)",
        description = "Remove um usuário do sistema",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> deletarUsuario(
        @Parameter(description = "ID do usuário a ser deletado") @PathVariable Integer id
    ) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
