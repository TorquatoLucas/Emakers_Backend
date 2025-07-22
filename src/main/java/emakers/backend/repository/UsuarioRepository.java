package emakers.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import emakers.backend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
}
