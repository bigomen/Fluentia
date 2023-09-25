package br.com.fluentia.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
public class AlunoAulaId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_aula", referencedColumnName = "id")
    private Aula aula;

//    @Column(name = "fk_aula")
//    private Long idAula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_aluno", referencedColumnName = "id")
    private Aluno aluno;

//    @Column(name = "fk_aluno")
//    private Long idAluno;

    public AlunoAulaId() {
    }

    public AlunoAulaId(Aula aula, Long aulaId, Aluno aluno, Long alunoId) {
        this.aula = aula;
//        this.idAula = aulaId;
        this.aluno = aluno;
//        this.idAluno = alunoId;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

//    public Long getIdAula() {
//        return idAula;
//    }
//
//    public void setIdAula(Long idAula) {
//        this.idAula = idAula;
//    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

//    public Long getIdAluno() {
//        return idAluno;
//    }
//
//    public void setIdAluno(Long idAluno) {
//        this.idAluno = idAluno;
//    }
}
