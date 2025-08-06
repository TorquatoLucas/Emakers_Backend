package emakers.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emakers.backend.dto.LoginRequest;
import emakers.backend.dto.LoginResponse;
import emakers.backend.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Tag(name = "Token", description = "Operações relacionadas a tokens")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/login")
    @Operation(
        summary = "Realizar login",
        description = "Faz login e recebe um token de volta"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(tokenService.login(loginRequest));
    } 

}
