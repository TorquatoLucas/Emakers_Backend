package emakers.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.dto.UsuarioDto;
import emakers.backend.mapper.UsuarioMapper;
import emakers.backend.model.Permissao;
import emakers.backend.model.Usuario;
import emakers.backend.repository.PermissaoRepository;
import emakers.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private UsuarioMapper usuarioMapper;

    @Transactional
    public Boolean salvarUsuario(UsuarioDto usuarioDto){

        var permissaoBasico = permissaoRepository.findByNome(Permissao.Valores.BASICO.name());


        if(!usuarioRepository.existsByEmail(usuarioDto.email())){
            Usuario usuario = usuarioMapper.toUsuario(usuarioDto);
            usuario.setSenha(bCryptPasswordEncoder.encode(usuarioDto.senha()));
            usuario.setPermissoes(Set.of(permissaoBasico));
            usuarioRepository.save(usuario);
            return true;
        }

        return false;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @Transactional
    public boolean deletarUsuario(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getPermissoes().clear(); // Remove os vínculos
            usuarioRepository.save(usuario); // Atualiza o usuário sem permissões
            usuarioRepository.delete(usuario); // Agora pode deletar
            return true;
        }
        return false;
    }

    @Transactional
    public Usuario atualizarUsuario(Integer id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + id + " não encontrado"));

        usuario.setCep( usuarioDto.cep() );
        usuario.setCpf( usuarioDto.cpf() );
        usuario.setEmail( usuarioDto.email() );
        usuario.setNome( usuarioDto.nome() );
        usuario.setSenha(bCryptPasswordEncoder.encode(usuarioDto.senha()));

        return usuarioRepository.save(usuario);
    }


}
