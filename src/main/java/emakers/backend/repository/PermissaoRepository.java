package emakers.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emakers.backend.model.Permissao;

@Repository
public interface PermissaoRepository extends JpaRepository<Permissao,Integer>{
    
    Permissao findByNome(String nome);

}
