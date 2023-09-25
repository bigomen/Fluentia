package br.com.fluentia.dto;

import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.AlunoAula;
import br.com.fluentia.model.AlunoAulaId;
import br.com.fluentia.model.Aula;

import java.io.Serial;
import java.io.Serializable;

public class AlunoAulaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private AulaDto aula;
    private Long idAula;
    private AlunoDto aluno;
    private Long idAluno;
    private Boolean presente;
    private Integer debito;
    private NivelDto nivel;
    private Long idNivel;
    private Integer compreensao;
    private Integer gramatica;
    private Integer escrita;
    private Integer pronunciacao;
    private Integer vocabulario;
    private String sugestao;
    private String pontoMelhoria;

    public AlunoAula dtoToModel(){
        AlunoAula model = new AlunoAula();
        Aluno aluno = new Aluno();
        Aula aula = new Aula();
        aula.setId(this.idAula);
        aluno.setId(this.idAluno);
        AlunoAulaId id = new AlunoAulaId(aula, null, aluno, null);
        model.setId(id);
        model.setPresente(this.presente);
        model.setDebito(this.debito);
        model.setNivel(null);
        model.setIdNivel(this.idNivel);
        model.setCompreensao(this.compreensao);
        model.setGramatica(this.gramatica);
        model.setEscrita(this.escrita);
        model.setPronunciacao(this.pronunciacao);
        model.setVocabulario(this.vocabulario);
        model.setSugestao(this.sugestao);
        model.setPontoMelhoria(this.pontoMelhoria);
        return model;
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

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Integer getDebito() {
        return debito;
    }

    public void setDebito(Integer debito) {
        this.debito = debito;
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

    public Integer getCompreensao() {
        return compreensao;
    }

    public void setCompreensao(Integer compreensao) {
        this.compreensao = compreensao;
    }

    public Integer getGramatica() {
        return gramatica;
    }

    public void setGramatica(Integer gramatica) {
        this.gramatica = gramatica;
    }

    public Integer getEscrita() {
        return escrita;
    }

    public void setEscrita(Integer escrita) {
        this.escrita = escrita;
    }

    public Integer getPronunciacao() {
        return pronunciacao;
    }

    public void setPronunciacao(Integer pronunciacao) {
        this.pronunciacao = pronunciacao;
    }

    public Integer getVocabulario() {
        return vocabulario;
    }

    public void setVocabulario(Integer vocabulario) {
        this.vocabulario = vocabulario;
    }

    public String getSugestao() {
        return sugestao;
    }

    public void setSugestao(String sugestao) {
        this.sugestao = sugestao;
    }

    public String getPontoMelhoria() {
        return pontoMelhoria;
    }

    public void setPontoMelhoria(String pontoMelhoria) {
        this.pontoMelhoria = pontoMelhoria;
    }
}
