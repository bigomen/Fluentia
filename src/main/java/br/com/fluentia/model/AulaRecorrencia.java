package br.com.fluentia.model;

import br.com.fluentia.dto.AulaRecorrenciaDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "aula_recorrencia")
public class AulaRecorrencia implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Integer ativo;

    @Column(name = "credito")
    private Integer credito;

    @Column(name = "duracao")
    private Integer duracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_modalidade", referencedColumnName = "id", updatable = false, insertable = false)
    private Modalidade modalidade;

    @Column(name = "fk_modalidade")
    private Long idModalidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_professor", referencedColumnName = "id", updatable = false, insertable = false)
    private Professor professor;

    @Column(name = "fk_professor")
    private Long idProfessor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_aula", referencedColumnName = "id", updatable = false, insertable = false)
    private TipoAula tipoAula;

    @Column(name = "fk_tipo_aula")
    private Long idTipoAula;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cron")
    private String cron;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "nivel_aula_recorrencia",
    joinColumns = @JoinColumn(name = "fk_aula", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "fk_nivel", referencedColumnName = "id"))
    private List<Nivel> nivelList;

    public AulaRecorrencia() {
    }

    public AulaRecorrencia(Long id, Integer credito, Integer duracao, Long idModalidade, Long idProfessor, Long idTipoAula, String nome, String cron) {
        this.id = id;
        this.credito = credito;
        this.duracao = duracao;
        this.idModalidade = idModalidade;
        this.idProfessor = idProfessor;
        this.idTipoAula = idTipoAula;
        this.nome = nome;
        this.cron = cron;
    }

    public AulaRecorrenciaDto modelToDto(){
        AulaRecorrenciaDto dto = new AulaRecorrenciaDto();
        dto.setId(this.id);
        dto.setAtivo(this.ativo);
        dto.setCredito(this.credito);
        dto.setDuracao(this.duracao);
        dto.setModalidade(this.modalidade.modelToDto());
        dto.setIdModalidade(this.idModalidade);
        dto.setProfessor(this.professor.modelToDto());
        dto.setIdProfessor(this.idProfessor);
        dto.setTipoAula(this.tipoAula.modelToDto());
        dto.setIdTipoAula(this.idTipoAula);
        dto.setNome(this.nome);
        dto.setCron(this.cron);
        dto.setNivelList(this.nivelList.stream().map(Nivel::modelToDto).collect(Collectors.toList()));
        return dto;
    }

    public Aula recorrenciaToAula(){
        Aula aula = new Aula();
        aula.setAtivo(this.ativo);
        aula.setCredito(this.credito);
        aula.setDuracao(this.duracao);
        aula.setModalidade(null);
        aula.setIdModalidade(this.idModalidade);
        aula.setProfessor(null);
        aula.setIdProfessor(this.idProfessor);
        aula.setTipoAula(null);
        aula.setIdTipoAula(this.idTipoAula);
        aula.setNome(this.nome);
        aula.setNivelList(this.nivelList);
        return aula;
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

    public Modalidade getModalidade() {
        return modalidade;
    }

    public void setModalidade(Modalidade modalidade) {
        this.modalidade = modalidade;
    }

    public Long getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(Long idModalidade) {
        this.idModalidade = idModalidade;
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

    public TipoAula getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(TipoAula tipoAula) {
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

    public List<Nivel> getNivelList() {
        return nivelList;
    }

    public void setNivelList(List<Nivel> nivelList) {
        this.nivelList = nivelList;
    }
}
