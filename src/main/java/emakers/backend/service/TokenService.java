package emakers.backend.service;

import java.time.Instant;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import emakers.backend.dto.LoginRequest;
import emakers.backend.dto.LoginResponse;
import emakers.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {

    private final JwtEncoder jwtEncoder;
    
    private final UsuarioRepository usuarioRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;





    public LoginResponse login(LoginRequest loginRequest){
        var usuario = usuarioRepository.findByEmail(loginRequest.email());

        if(usuario.isEmpty() || !usuario.get().loginCorreto(loginRequest, bCryptPasswordEncoder)){
            throw new BadCredentialsException("e-mail ou senha inválidos");
        }

        var agora = Instant.now();
        var expiraEm = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("emakers")
                .subject(usuario.get().getId().toString())
                .issuedAt(agora)
                .expiresAt(agora.plusSeconds(expiraEm))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); 

        return new LoginResponse(jwtValue, expiraEm);
    }

}
