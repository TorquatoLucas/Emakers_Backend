package emakers.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import emakers.backend.dto.UsuarioDto;
import emakers.backend.dto.UsuarioResponse;
import emakers.backend.mapper.UsuarioMapper;
import emakers.backend.model.Permissao;
import emakers.backend.model.Usuario;
import emakers.backend.repository.PermissaoRepository;
import emakers.backend.repository.UsuarioRepository;
import emakers.backend.viacep.Endereco;
import emakers.backend.viacep.ViaCep;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    private final PermissaoRepository permissaoRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UsuarioMapper usuarioMapper;

    private final ViaCep viaCep;

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
    public UsuarioResponse buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuário com id " + id + " não encontrado."));
            // FAZER UM GLOBAL EXCEPTION HANDLER DPS

        return gerarResponse(usuario);

    }


    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> respostas = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            respostas.add(gerarResponse(usuario));
        }

        return respostas;
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




    private UsuarioResponse gerarResponse(Usuario usuario){
        UsuarioResponse response = new UsuarioResponse(
                usuario.getCpf(),
                gerarEndereco(usuario.getCep()),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha()
            );

        return response;
    }


    private Endereco gerarEndereco(String cep){
        Endereco endereco;

        try {
            endereco = viaCep.gerarEndereco(cep);
        } catch (Exception e) {
            endereco = new Endereco(cep, null, null, null, null);
        }

        if(endereco.cep() == null){
            return new Endereco(cep, null, null, null, null);
        }

        return endereco;
    }
}
