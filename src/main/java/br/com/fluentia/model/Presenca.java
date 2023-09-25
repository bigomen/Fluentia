package br.com.fluentia.model;

import br.com.fluentia.dto.PresencaDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "presenca")
public class Presenca implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aula", referencedColumnName = "id", updatable = false, insertable = false)
	private Aula aula;

	@Column(name = "id_aula",nullable = false)
	private Long idAula;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno", referencedColumnName = "id", updatable = false, insertable = false)
	private Aluno aluno;

	@Column(name = "id_aluno",nullable = false)
	private Long idAluno;

	public PresencaDto dtoToModel(){
		PresencaDto dto = new PresencaDto();
		dto.setId(this.id);
		dto.setAula(null);
		dto.setIdAula(this.idAula);
		dto.setAluno(null);
		dto.setIdAluno(this.idAluno);
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
}
