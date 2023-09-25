package br.com.fluentia.model;

import br.com.fluentia.dto.ModalidadeDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "modalidade")
public class Modalidade implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    public ModalidadeDto modelToDto(){
        ModalidadeDto dto = new ModalidadeDto();
        dto.setId(this.id);
        dto.setDescricao(this.descricao);
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
