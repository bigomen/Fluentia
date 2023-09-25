package br.com.fluentia.dto;

import br.com.fluentia.model.AulaRecorrencia;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class AulaRecorrenciaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer ativo;
    private Integer credito;
    private Integer duracao;
    private ModalidadeDto modalidade;
    private Long idModalidade;
    private ProfessorDto professor;
    private Long idProfessor;
    private TipoAulaDto tipoAula;
    private Long idTipoAula;
    private String nome;
    private String cron;
    private List<NivelDto> nivelList;
    public AulaRecorrencia dtoToModel(){
        AulaRecorrencia model = new AulaRecorrencia();
        model.setId(this.id);
        model.setAtivo(this.ativo);
        model.setCredito(this.credito);
        model.setDuracao(this.duracao);
        model.setModalidade(null);
        model.setIdModalidade(this.idModalidade);
        model.setProfessor(null);
        model.setIdProfessor(this.idProfessor);
        model.setTipoAula(null);
        model.setIdTipoAula(this.idTipoAula);
        model.setNome(this.nome);
        model.setCron(this.cron);
        if(this.nivelList != null){
            model.setNivelList(this.nivelList.stream().map(NivelDto::dtoToModel).collect(Collectors.toList()));
        }
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAtivo() {
        return ativo;
    }

    public void setAtivo(Integer ativo) {
        this.ativo = ativo;
    }

    public Integer getCredito() {
        return credito;
    }

    public void setCredito(Integer credito) {
        this.credito = credito;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public ModalidadeDto getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeDto modalidade) {
        this.modalidade = modalidade;
    }

    public Long getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Long idModalidade) {
        this.idModalidade = idModalidade;
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

    public TipoAulaDto getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(TipoAulaDto tipoAula) {
        this.tipoAula = tipoAula;
    }

    public Long getIdTipoAula() {
        return idTipoAula;
    }

    public void setIdTipoAula(Long idTipoAula) {
        this.idTipoAula = idTipoAula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public List<NivelDto> getNivelList() {
        return nivelList;
    }

    public void setNivelList(List<NivelDto> nivelList) {
        this.nivelList = nivelList;
    }
}
