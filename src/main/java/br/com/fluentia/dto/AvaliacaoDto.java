package br.com.fluentia.dto;

import br.com.fluentia.model.Avaliacao;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AvaliacaoDto {
	

	private Long id;

	@NotNull(message = "Professor")
	private Long idProfessor;
	private Long idAula;

	@NotNull(message = "Aluno")
	private Long idAluno;

	private ProfessorDto professor;
	private AulaDto aula;
	private AlunoDto aluno;
	private Integer pointGrammar;
	private Integer pointComprehension;
	private Integer pointPronunciation;
	private Integer pointDiscourse;
	private Integer pointLexicalResource;
	private String needsImprovement;
	private String writeGood;
	private String sugestion;
	private String mesAvaliacao;
	private String dataFormatada;

	public AvaliacaoDto() {
	}

	public String getMesAvaliacao() {
		return mesAvaliacao;
	}
	public void setMesAvaliacao(String mesAvaliacao) {
		this.mesAvaliacao = mesAvaliacao;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdProfessor() {
		return idProfessor;
	}
	public void setIdProfessor(Long idProfessor) {
		this.idProfessor = idProfessor;
	}
	public Long getIdAula() {
		return idAula;
	}
	public void setIdAula(Long idAula) {
		this.idAula = idAula;
	}
	public Long getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}
	public Integer getPointGrammar() {
		return pointGrammar;
	}
	public void setPointGrammar(Integer pointGrammar) {
		this.pointGrammar = pointGrammar;
	}
	public Integer getPointComprehension() {
		return pointComprehension;
	}
	public void setPointComprehension(Integer pointComprehension) {
		this.pointComprehension = pointComprehension;
	}
	public Integer getPointPronunciation() {
		return pointPronunciation;
	}
	public void setPointPronunciation(Integer pointPronunciation) {
		this.pointPronunciation = pointPronunciation;
	}
	public Integer getPointDiscourse() {
		return pointDiscourse;
	}
	public void setPointDiscourse(Integer pointDiscourse) {
		this.pointDiscourse = pointDiscourse;
	}
	public Integer getPointLexicalResource() {
		return pointLexicalResource;
	}
	public void setPointLexicalResource(Integer pointLexicalResource) {
		this.pointLexicalResource = pointLexicalResource;
	}
	public String getNeedsImprovement() {
		return needsImprovement;
	}
	public void setNeedsImprovement(String needsImprovement) {
		this.needsImprovement = needsImprovement;
	}
	public String getWriteGood() {
		return writeGood;
	}
	public void setWriteGood(String writeGood) {
		this.writeGood = writeGood;
	}
	public String getSugestion() {
		return sugestion;
	}
	public void setSugestion(String sugestion) {
		this.sugestion = sugestion;
	}
	public AulaDto getAula() {
		return aula;
	}
	public void setAula(AulaDto aula) {
		this.aula = aula;
	}
	public AlunoDto getAluno() {
		return aluno;
	}
	public void setAluno(AlunoDto aluno) {
		this.aluno = aluno;
	}
	public String getDataFormatada() {
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}

	public ProfessorDto getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDto professor) {
		this.professor = professor;
	}

	public Avaliacao dtoToModel () {
		Avaliacao model = new Avaliacao();
		model.setId(this.id);
		model.setAluno(null);
		model.setIdAluno(this.idAluno);
		model.setAula(null);
		model.setIdAula(this.idAula);
		model.setProfessor(null);
		model.setIdProfessor(this.idProfessor);
		model.setMesAvaliacao(this.mesAvaliacao );
		model.setNeedsImprovement(this.needsImprovement);
		model.setSugestion(this.sugestion);
		model.setWriteGood(this.writeGood);
		model.setPointComprehension(this.pointComprehension);
		model.setPointDiscourse(this.pointDiscourse);
		model.setPointGrammar(this.pointGrammar);
		model.setPointLexicalResource(this.pointLexicalResource);
		model.setPointPronunciation(this.pointPronunciation);
		return model;
	}
}
