package br.com.fluentia.model;

import br.com.fluentia.dto.CandoDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "cando")
public class Cando implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "conteudo")
    private String conteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_nivel", referencedColumnName = "id", updatable = false, insertable = false)
    private Nivel nivel;

    @Column(name = "fk_nivel")
    private Long idNivel;

    public CandoDto modelToDto(){
        CandoDto dto = new CandoDto();
        dto.setId(this.id);
        dto.setDescricao(this.descricao);
        dto.setConteudo(this.conteudo);
        if(this.nivel != null){
            dto.setNivel(this.nivel.modelToDto());
        }
        dto.setIdNivel(this.idNivel);
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

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }

    public Long getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Long idNivel) {
        this.idNivel = idNivel;
    }
}
