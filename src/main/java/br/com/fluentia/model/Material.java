package br.com.fluentia.model;

import br.com.fluentia.dto.MaterialDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "material")
public class Material implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_professor", referencedColumnName = "id", updatable = false, insertable = false)
    private Professor professor;

    @Column(name = "fk_professor")
    private Long idProfessor;

    public MaterialDto modelToDto(){
        MaterialDto dto = new MaterialDto();
        dto.setId(this.id);
        dto.setDescricao(this.descricao);
        dto.setUrl(this.url);
        if(this.professor != null){
            dto.setProfessor(this.professor.modelToDto());
        }
        dto.setIdProfessor(this.idProfessor);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }
}
