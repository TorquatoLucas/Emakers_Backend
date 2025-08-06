package emakers.backend.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.model.Permissao;
import emakers.backend.model.Usuario;
import emakers.backend.repository.PermissaoRepository;
import emakers.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class UsuarioAdmConfig implements CommandLineRunner{

    private PermissaoRepository permissaoRepository;
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        var permissaoAdm = permissaoRepository.findByNome(Permissao.Valores.ADMIN.name());

        var usuarioAdm = usuarioRepository.findByEmail("adm@adm.com");

        usuarioAdm.ifPresentOrElse(
                usuario -> {
                    System.out.println("administrador já existe");
                },
                () -> {
                    var usuario = new Usuario();
                    usuario.setNome("adm");
                    usuario.setCpf("01234567899");
                    usuario.setEmail("adm@adm.com");
                    usuario.setSenha(passwordEncoder.encode("adm"));
                    usuario.setPermissoes(Set.of(permissaoAdm));
                    usuarioRepository.save(usuario);
                });
        
    }
    
}
