package emakers.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emakers.backend.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo,Integer>{
    
    Optional<Emprestimo> findByLivroIdAndUsuarioIdAndDevolvidoFalse(Integer livroId, Integer usuarioId);

}
