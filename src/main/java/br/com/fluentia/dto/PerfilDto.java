package br.com.fluentia.dto;

import br.com.fluentia.model.Perfil;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

public class PerfilDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private Long id;
	private ProfessorDto professor;

	@NotNull(message = "Professor")
	private Long idProfessor;
	private AlunoDto aluno;

	@NotNull(message = "Aluno")
	private Long idAluno;
	private String geral;
	private String musica;
	private String hobbies;
	private String familia;
	private String filmes;
	private String personalidade;
	private Long userCreateAt;
	private Date createAt;

	public PerfilDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getGeral() {
		return geral;
	}

	public void setGeral(String geral) {
		this.geral = geral;
	}

	public String getMusica() {
		return musica;
	}

	public void setMusica(String musica) {
		this.musica = musica;
	}

	public String getHobbies() {
		return hobbies;
	}

	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getFilmes() {
		return filmes;
	}

	public void setFilmes(String filmes) {
		this.filmes = filmes;
	}

	public String getPersonalidade() {
		return personalidade;
	}

	public void setPersonalidade(String personalidade) {
		this.personalidade = personalidade;
	}

	public Long getUserCreateAt() {
		return userCreateAt;
	}

	public void setUserCreateAt(Long userCreateAt) {
		this.userCreateAt = userCreateAt;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Perfil dtoToModel() {
		Perfil model = new Perfil();
		model.setId(this.id);
		model.setProfessor(null);
		model.setIdProfessor(this.idProfessor);
		model.setAluno(null);
		model.setIdAluno(this.idAluno);
		model.setGeral(this.geral);
		model.setMusica(this.musica);
		model.setHobbies(this.hobbies);
		model.setFamilia(this.familia);
		model.setFilmes(this.filmes);
		model.setPersonalidade(this.personalidade);
		return model;
	}
}
