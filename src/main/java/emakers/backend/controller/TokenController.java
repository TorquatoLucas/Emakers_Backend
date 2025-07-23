package emakers.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import emakers.backend.dto.LoginRequest;
import emakers.backend.dto.LoginResponse;
import emakers.backend.service.TokenService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(tokenService.login(loginRequest));
    } 

}
