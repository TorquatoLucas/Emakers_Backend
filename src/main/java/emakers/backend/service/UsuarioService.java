package emakers.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.dto.UsuarioDto;
import emakers.backend.mapper.UsuarioMapper;
import emakers.backend.model.Usuario;
import emakers.backend.repository.UsuarioRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    private UsuarioMapper usuarioMapper;

    @Transactional
    public Usuario salvarUsuario(UsuarioDto usuarioDto){

        Usuario usuario = usuarioMapper.toUsuario(usuarioDto);

        return usuarioRepository.save(usuario);
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
