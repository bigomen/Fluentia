package br.com.fluentia.model;

import br.com.fluentia.dto.AlunoAulaDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "aluno_aula")
public class AlunoAula implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private AlunoAulaId id;

    @Column(name = "presente")
    private Boolean presente;

    @Column(name = "debito")
    private Integer debito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_nivel", referencedColumnName = "id", updatable = false, insertable = false)
    private Nivel nivel;

    @Column(name = "fk_nivel")
    private Long idNivel;

    @Column(name = "compreensao")
    private Integer compreensao;

    @Column(name = "gramatica")
    private Integer gramatica;

    @Column(name = "escrita")
    private Integer escrita;

    @Column(name = "pronunciacao")
    private Integer pronunciacao;

    @Column(name = "vocabulario")
    private Integer vocabulario;

    @Column(name = "sugestao")
    private String sugestao;

    @Column(name = "ponto_melhoria")
    private String pontoMelhoria;

    public AlunoAulaDto modelToDto(){
        AlunoAulaDto dto = new AlunoAulaDto();
        if(this.id.getAula() != null){
            dto.setAula(this.id.getAula().modelToDto());
        }
        dto.setIdAula(this.id.getAula().getId());
        if(this.id.getAluno() != null){
            dto.setAluno(this.id.getAluno().modelToDto());
        }
        dto.setIdAluno(this.id.getAluno().getId());
        dto.setPresente(this.presente);
        dto.setDebito(this.debito);
        if(this.nivel != null){
            dto.setNivel(this.nivel.modelToDto());
        }
        dto.setIdNivel(this.idNivel);
        dto.setCompreensao(this.compreensao);
        dto.setGramatica(this.gramatica);
        dto.setEscrita(this.escrita);
        dto.setPronunciacao(this.pronunciacao);
        dto.setVocabulario(this.vocabulario);
        dto.setSugestao(this.sugestao);
        dto.setPontoMelhoria(this.pontoMelhoria);
        return dto;
    }

    public AlunoAula(){

    }

    public AlunoAula(Long idAula, String nomeAula, Long idNivel){
        this.setIdNivel(idNivel);
        Aula aula = new Aula(idAula, nomeAula);
        this.id = new AlunoAulaId(aula, null, null, null);
    }

    @PrePersist
    private void prePersist(){
        this.presente = false;
    }

    public AlunoAulaId getId() {
        return id;
    }

    public void setId(AlunoAulaId id) {
        this.id = id;
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
