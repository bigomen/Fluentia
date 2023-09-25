package br.com.fluentia.model;

import br.com.fluentia.dto.NivelDto;
import br.com.fluentia.type.StatusNivelEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "nivel")
public class Nivel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ativo")
    private Integer ativo;

    @Column(name = "nome")
    private String nome;

    @Column(name = "quantidade_horas")
    private Integer quantidadeHoras;

    public NivelDto modelToDto(){
        NivelDto dto = new NivelDto();
        dto.setId(this.id);
        dto.setAtivo(this.ativo);
        dto.setNome(this.nome);
        dto.setQuantidadeHoras(this.quantidadeHoras);
        return dto;
    }

    @PrePersist
    private void PrePersist(){
        this.ativo = StatusNivelEnum.ATIVO.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(Integer quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }
}
