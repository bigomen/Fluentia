package br.com.fluentia.dto;

import br.com.fluentia.model.Credito;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class CreditoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer credito;
    private LocalDateTime dateTime;
    private AlunoDto aluno;
    private Long idAluno;
    private AlunoPlanoDto alunoPlano;
    private String idAlunoPlano;

    public Credito dtoToModel(){
        Credito model = new Credito();
        model.setId(this.id);
        model.setCredito(this.credito);
        model.setDateTime(this.dateTime);
        model.setAluno(null);
        model.setIdAluno(this.idAluno);
        model.setAlunoPlano(null);
        model.setIdAlunoPlano(this.idAlunoPlano);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public AlunoPlanoDto getAlunoPlano() {
        return alunoPlano;
    }

    public void setAlunoPlano(AlunoPlanoDto alunoPlano) {
        this.alunoPlano = alunoPlano;
    }

    public String getIdAlunoPlano() {
        return idAlunoPlano;
    }

    public void setIdAlunoPlano(String idAlunoPlano) {
        this.idAlunoPlano = idAlunoPlano;
    }
}
