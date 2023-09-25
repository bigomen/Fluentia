package br.com.fluentia.dto;

import br.com.fluentia.model.Presenca;

import java.io.Serial;
import java.io.Serializable;

public class PresencaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private AulaDto aula;
    private Long idAula;
    private AlunoDto aluno;
    private Long idAluno;

    public Presenca dtoToModel(){
        Presenca model = new Presenca();
        model.setId(this.id);
        model.setAula(null);
        model.setIdAula(this.idAula);
        model.setAluno(null);
        model.setIdAluno(this.idAluno);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AulaDto getAula() {
        return aula;
    }

    public void setAula(AulaDto aula) {
        this.aula = aula;
    }

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public AlunoDto getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDto aluno) {
        this.aluno = aluno;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }
}
