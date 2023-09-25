package br.com.fluentia.dto;

import br.com.fluentia.model.Material;

import java.io.Serial;
import java.io.Serializable;

public class MaterialDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String descricao;
    private String url;
    private ProfessorDto professor;
    private Long idProfessor;

    public Material dtoToModel(){
        Material model = new Material();
        model.setId(this.id);
        model.setDescricao(this.descricao);
        model.setUrl(this.url);
        model.setProfessor(null);
        model.setIdProfessor(this.idProfessor);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProfessorDto getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDto professor) {
        this.professor = professor;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }
}
