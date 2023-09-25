package br.com.fluentia.dto;

import br.com.fluentia.model.Aula;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AulaDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer ativo;

	@NotNull(message = "Crédito")
	private Integer credito;
	private String descricao;
	private Integer duracao;
	private ModalidadeDto modalidade;

	@NotNull(message = "Modalidade")
	private Long idModalidade;
	private ProfessorDto professor;

	@NotNull(message = "Professor")
	private Long idProfessor;
	private TemaDto tema;
	private Long idTema;
	private TipoAulaDto tipoAula;

	@NotNull(message = "Tipo aula")
	private Long idTipoAula;

	@NotEmpty(message = "Nome")
	@NotBlank(message = "Nome")
	private String nome;
	private String linkGoogleClass;
	private String joinUrl;
	private String startUrl;
	private LocalDateTime dataAula;
	private List<MaterialDto> materialList;
	private List<NivelDto> nivelList;
	private Integer alunos;

	private String status;

	public AulaDto() {
	}

	public Aula dtoToModel(){
		Aula model = new Aula();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setCredito(this.credito);
		model.setDescricao(this.descricao);
		model.setDuracao(this.duracao);
		model.setModalidade(null);
		model.setIdModalidade(this.idModalidade);
		model.setProfessor(null);
		model.setIdProfessor(this.idProfessor);
		model.setTema(null);
		model.setIdTema(this.idTema);
		model.setTipoAula(null);
		model.setIdTipoAula(this.idTipoAula);
		model.setNome(this.nome);
		model.setLinkGoogleClass(this.linkGoogleClass);
		model.setJoinUrl(this.joinUrl);
		model.setStartUrl(this.startUrl);
		model.setDataAula(this.dataAula);
		if(this.materialList != null){
			model.setMaterialList(this.materialList.stream().map(MaterialDto::dtoToModel).collect(Collectors.toList()));
		}
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

	public TemaDto getTema() {
		return tema;
	}

	public void setTema(TemaDto tema) {
		this.tema = tema;
	}

	public Long getIdTema() {
		return idTema;
	}

	public void setIdTema(Long idTema) {
		this.idTema = idTema;
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

	public List<MaterialDto> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialDto> materialList) {
		this.materialList = materialList;
	}

	public List<NivelDto> getNivelList() {
		return nivelList;
	}

	public void setNivelList(List<NivelDto> nivelList) {
		this.nivelList = nivelList;
	}

	public Integer getAlunos() {
		return alunos;
	}

	public void setAlunos(Integer alunos) {
		this.alunos = alunos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
