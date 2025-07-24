package emakers.backend.service;

import java.util.List;
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
    public boolean deletarUsuario(Integer id){
        if(usuarioRepository.existsById(id)){
            usuarioRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

}
