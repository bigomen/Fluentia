package br.com.fluentia.model;

import br.com.fluentia.dto.AulaDto;
import br.com.fluentia.type.StatusAulaEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "aula")
public class Aula implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "ativo")
	private Integer ativo;

	@Column(name = "credito")
	private Integer credito;

	@Column(name = "descricao")
	private String descricao;

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
	@JoinColumn(name = "fk_tema", referencedColumnName = "id", updatable = false, insertable = false)
	private Tema tema;

	@Column(name = "fk_tema")
	private Long idTema;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_tipo_aula", referencedColumnName = "id", updatable = false, insertable = false)
	private TipoAula tipoAula;

	@Column(name = "fk_tipo_aula")
	private Long idTipoAula;

	@Column(name = "nome")
	private String nome;

	@Column(name = "link_google_class")
	private String linkGoogleClass;

	@Column(name = "join_url")
	private String joinUrl;

	@Column(name = "start_url")
	private String startUrl;

	@Column(name = "data_aula")
	private LocalDateTime dataAula;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "material_aula",
			joinColumns = @JoinColumn(name = "fk_aula", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "fk_material", referencedColumnName = "id"))
	private List<Material> materialList;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "nivel_aula",
			joinColumns = @JoinColumn(name = "fk_aula", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "fk_nivel", referencedColumnName = "id"))
	private List<Nivel> nivelList;

	public AulaDto modelToDto(){
		AulaDto dto = new AulaDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setCredito(this.credito);
		dto.setDescricao(this.descricao);
		dto.setDuracao(this.duracao);
		if(this.modalidade != null){
			dto.setModalidade(this.getModalidade().modelToDto());
		}
		dto.setIdModalidade(this.idModalidade);
		if(this.professor != null){
			dto.setProfessor(this.professor.modelToDto());
		}
		dto.setIdProfessor(this.idProfessor);
		if(this.tema != null){
			dto.setTema(this.tema.modelToDto());
		}
		dto.setIdTema(this.idTema);
		if(this.tipoAula != null){
			dto.setTipoAula(this.tipoAula.modelToDto());
		}
		dto.setIdTipoAula(this.idTipoAula);
		dto.setNome(this.nome);
		dto.setLinkGoogleClass(this.linkGoogleClass);
		dto.setJoinUrl(this.joinUrl);
		dto.setStartUrl(this.startUrl);
		dto.setDataAula(this.dataAula);
		if(this.materialList != null){
			dto.setMaterialList(this.materialList.stream().map(Material::modelToDto).collect(Collectors.toList()));
		}
		if(this.nivelList != null){
			dto.setNivelList(this.nivelList.stream().map(Nivel::modelToDto).collect(Collectors.toList()));
		}
		return dto;
	}

	public Aula(){

	}

	public Aula(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Aula(Long id, String nome, LocalDateTime dataAula) {
		this.id = id;
		this.nome = nome;
		this.dataAula = dataAula;
	}

	public Aula(Long id, LocalDateTime dataAula, String joinUrl, String professor){
		this.id = id;
		this.dataAula = dataAula;
		this.joinUrl = joinUrl;
		Professor prof = new Professor();
		prof.setNome(professor);
		this.professor = prof;
	}

	@PrePersist
	private void PrePersist(){
		this.ativo = StatusAulaEnum.ATIVO.getStatus();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
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

	public String getLinkGoogleClass() {
		return linkGoogleClass;
	}

	public void setLinkGoogleClass(String linkGoogleClass) {
		this.linkGoogleClass = linkGoogleClass;
	}

	public String getJoinUrl() {
		return joinUrl;
	}

	public void setJoinUrl(String joinUrl) {
		this.joinUrl = joinUrl;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public LocalDateTime getDataAula() {
		return dataAula;
	}

	public void setDataAula(LocalDateTime dataAula) {
		this.dataAula = dataAula;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	public List<Nivel> getNivelList() {
		return nivelList;
	}

	public void setNivelList(List<Nivel> nivelList) {
		this.nivelList = nivelList;
	}
}
