package br.com.fluentia.model;

import br.com.fluentia.dto.PerfilDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "perfil")
public class Perfil implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_professor", referencedColumnName = "id", updatable = false, insertable = false)
	private Professor professor;

	@Column(name = "fk_professor", nullable = false)
	private Long idProfessor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_aluno", referencedColumnName = "id", updatable = false, insertable = false)
	private Aluno aluno;

	@Column(name = "fk_aluno", nullable = false)
	private Long idAluno;
	@Column
	private String geral;
	@Column
	private String musica;
	@Column
	private String hobbies;
	@Column
	private String familia;
	@Column
	private String filmes;
	@Column
	private String personalidade;
	@Column(name = "user_create_at")
	private Long userCreateAt;
	@Column(name = "create_at")
	private LocalDate createAt;

	@PrePersist
	private void prePersist(){
		this.createAt = LocalDate.now();
	}

	public Long getUserCreateAt() {
		return userCreateAt;
	}

	public void setUserCreateAt(Long userCreateAt) {
		this.userCreateAt = userCreateAt;
	}

	public LocalDate getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDate createAt) {
		this.createAt = createAt;
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

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public PerfilDto modelToDto() {
		PerfilDto dto = new PerfilDto();
		dto.setId(this.id);
		if(this.professor != null){
			dto.setProfessor(this.professor.modelToDto());
		}
		dto.setIdProfessor(this.idProfessor);
		if(this.aluno != null){
			dto.setAluno(this.aluno.modelToDto());
		}
		dto.setIdAluno(this.idAluno);
		dto.setGeral(this.geral);
		dto.setMusica(this.musica);
		dto.setHobbies(this.hobbies);
		dto.setFamilia(this.familia);
		dto.setFilmes(this.filmes);
		dto.setPersonalidade(this.personalidade);
		return dto;
	}

	
}
