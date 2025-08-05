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
import emakers.backend.exception.ConflictException;
import emakers.backend.exception.IdNotFoundException;
import emakers.backend.exception.ValidationException;
import emakers.backend.mapper.UsuarioMapper;
import emakers.backend.model.Permissao;
import emakers.backend.model.Usuario;
import emakers.backend.repository.PermissaoRepository;
import emakers.backend.repository.UsuarioRepository;
import emakers.backend.viacep.Endereco;
import emakers.backend.viacep.ViaCep;
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
    public Usuario salvarUsuario(UsuarioDto usuarioDto){

        var permissaoBasico = permissaoRepository.findByNome(Permissao.Valores.BASICO.name());

        if(usuarioDto.cpf().length() == 11){
            if(!usuarioRepository.existsByEmail(usuarioDto.email())){
                Usuario usuario = usuarioMapper.toUsuario(usuarioDto);
                usuario.setSenha(bCryptPasswordEncoder.encode(usuarioDto.senha()));
                usuario.setPermissoes(Set.of(permissaoBasico));
                return usuarioRepository.save(usuario);
            }

            throw new ConflictException();
        
        }

        throw new ValidationException();

    }

    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);

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
    public void deletarUsuario(Integer id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.getPermissoes().clear();
            usuarioRepository.save(usuario);
            usuarioRepository.delete(usuario);
        }else{
            throw new IdNotFoundException();
        }
    }

    @Transactional
    public Usuario atualizarUsuario(Integer id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);

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
