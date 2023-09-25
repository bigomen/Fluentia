package br.com.fluentia.dto;

public class AlunoAssinaturaDto {

    private String token;
    private AlunoDto aluno;
    private Long plano;
    private Long preMatricula;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AlunoDto getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDto aluno) {
        this.aluno = aluno;
    }

    public Long getPlano() {
        return plano;
    }

    public void setPlano(Long plano) {
        this.plano = plano;
    }

    public Long getPreMatricula() {
        return preMatricula;
    }

    public void setPreMatricula(Long preMatricula) {
        this.preMatricula = preMatricula;
    }
}
