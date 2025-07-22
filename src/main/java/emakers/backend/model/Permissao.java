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
@Table(name = "permissao")
public class Permissao {
    
    @Id
    @Column(name = "permissao_id")
    private Integer id;

    private String nome;

    public enum Valores{
        BASICO(1),
        ADM(2);

        Integer permissaoId;

        Valores(Integer permissaoId){
            this.permissaoId = permissaoId;
        }

        Integer getPermissaoId(){
            return permissaoId;
        }
    }

}
