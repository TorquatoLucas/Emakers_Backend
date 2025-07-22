package emakers.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emakers.backend.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Integer>{
    
}
