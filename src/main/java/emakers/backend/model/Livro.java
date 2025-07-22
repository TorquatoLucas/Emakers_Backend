package emakers.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "livro")
public class Livro {
    
    @Id
    @Column(name = "livro_id")
    private int id;

    private String nome;
    private String autor;
    
    @Column(name = "data_lancamento")
    private String data;
    
}
