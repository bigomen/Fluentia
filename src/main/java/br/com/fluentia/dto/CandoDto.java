package br.com.fluentia.dto;


import br.com.fluentia.model.Cando;

import java.io.Serial;
import java.io.Serializable;

public class CandoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private String conteudo;
    private NivelDto nivel;
    private Long idNivel;

    public Cando dtoToModel(){
        Cando model = new Cando();
        model.setId(this.id);
        model.setDescricao(this.descricao);
        model.setConteudo(this.conteudo);
        model.setNivel(null);
        model.setIdNivel(this.idNivel);
        return model;
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

    public NivelDto getNivel() {
        return nivel;
    }

    public void setNivel(NivelDto nivel) {
        this.nivel = nivel;
    }

    public Long getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(Long idNivel) {
        this.idNivel = idNivel;
    }
}
