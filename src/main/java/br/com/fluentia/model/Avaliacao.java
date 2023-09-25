package br.com.fluentia.model;

import br.com.fluentia.dto.AvaliacaoDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="avaliacao")
public class Avaliacao implements Serializable {

	
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_professor", referencedColumnName = "id", updatable = false, insertable = false)
	private Professor professor;

	@Column(name="id_professor",nullable = false)
	private Long idProfessor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aula", referencedColumnName = "id", updatable = false, insertable = false)
	private Aula aula;

	@Column(name="id_aula",nullable = true)
	private Long idAula;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno", referencedColumnName = "id", updatable = false, insertable = false)
	private Aluno aluno;

	@Column(name="id_aluno",nullable = false)
	private Long idAluno;
	@Column(name="point_grammar",nullable = true)
	private Integer pointGrammar;
	@Column(name="point_comprehension",nullable = true)
	private Integer pointComprehension;
	@Column(name="point_pronunciation",nullable = true)
	private Integer pointPronunciation;
	@Column(name="point_discourse",nullable = true)
	private Integer pointDiscourse;
	@Column(name="point_lexical",nullable = true)
	private Integer pointLexicalResource;
	@Column(name="improvement",nullable = true)
	private String needsImprovement;
	@Column(name="write_good",nullable = true)
	private String writeGood;
	@Column(nullable = true)
	private String sugestion;
	@Column(name="mes_avaliacao",nullable = true)
	private String mesAvaliacao;
    
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
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
	public String getMesAvaliacao() {
		return mesAvaliacao;
	}
	public void setMesAvaliacao(String mesAvaliacao) {
		this.mesAvaliacao = mesAvaliacao;
	}

	public AvaliacaoDto modelToDto () {
		AvaliacaoDto dto = new AvaliacaoDto();
		dto.setId(this.id);
		if(this.aluno != null){
			dto.setAluno(this.aluno.modelToDto());
		}
		dto.setIdAluno(this.idAluno);
		if(this.aula != null){
			dto.setAula(this.aula.modelToDto());
		}
		dto.setIdAula(this.idAula);
		if(this.professor != null){
			dto.setProfessor(this.professor.modelToDto());
		}
		dto.setIdProfessor(this.idProfessor);
		dto.setMesAvaliacao(this.mesAvaliacao );
		dto.setNeedsImprovement(this.needsImprovement);
		dto.setSugestion(this.sugestion);
		dto.setWriteGood(this.writeGood);
		dto.setPointComprehension(this.pointComprehension);
		dto.setPointDiscourse(this.pointDiscourse);
		dto.setPointGrammar(this.pointGrammar);
		dto.setPointLexicalResource(this.pointLexicalResource);
		dto.setPointPronunciation(this.pointPronunciation);
		return dto;
	}
	
	
}
