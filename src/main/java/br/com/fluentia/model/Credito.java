package br.com.fluentia.model;

import br.com.fluentia.dto.CreditoDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "credito")
public class Credito implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "credito")
	private Integer credito;

	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_aluno", referencedColumnName = "id", updatable = false, insertable = false)
	private Aluno aluno;

	@Column(name = "fk_aluno")
	private Long idAluno;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_plano", referencedColumnName = "codigo", updatable = false, insertable = false)
	private AlunoPlano alunoPlano;

	@Column(name = "fk_plano")
	private String idAlunoPlano;

	public Credito() {
	}

	public Credito(Integer credito, LocalDateTime dateTime) {
		this.credito = credito;
		this.dateTime = dateTime;
	}

	public CreditoDto modelToDto(){
		CreditoDto dto = new CreditoDto();
		dto.setId(this.id);
		dto.setCredito(this.credito);
		dto.setDateTime(this.dateTime);
		if(this.aluno != null){
			dto.setAluno(this.aluno.modelToDto());
		}
		dto.setIdAluno(this.idAluno);
		if(this.alunoPlano != null){
			dto.setAlunoPlano(this.alunoPlano.modelToDto());
		}
		dto.setIdAlunoPlano(this.idAlunoPlano);
		return dto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCredito() {
		return credito;
	}

	public void setCredito(Integer credito) {
		this.credito = credito;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public AlunoPlano getAlunoPlano() {
		return alunoPlano;
	}

	public void setAlunoPlano(AlunoPlano alunoPlano) {
		this.alunoPlano = alunoPlano;
	}

	public String getIdAlunoPlano() {
		return idAlunoPlano;
	}

	public void setIdAlunoPlano(String idAlunoPlano) {
		this.idAlunoPlano = idAlunoPlano;
	}
}
