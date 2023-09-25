package br.com.fluentia.dto;

import java.util.List;

public class AlunoPresencaDto {

    private Long idAula;
    private List<AlunoAulaDto> alunos;

    public Long getIdAula() {
        return idAula;
    }

    public void setIdAula(Long idAula) {
        this.idAula = idAula;
    }

    public List<AlunoAulaDto> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<AlunoAulaDto> alunos) {
        this.alunos = alunos;
    }
}
