package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;


public class RelatorioPresencaDto implements Serializable {
	public RelatorioPresencaDto(Long idAula, String dataAula, String professor, Boolean presenca, String nomeAula) {
		this.idAula = idAula;
		this.dataAula = dataAula;
		this.professor = professor;
		this.presenca = presenca;
		this.nomeAula = nomeAula;
	}

	@Serial
	private static final long serialVersionUID = 1L;

	@JsonProperty("idAula")
	private Long idAula;

	@JsonProperty("dataAula")
	private String dataAula;

	@JsonProperty("professor")
	private String professor;

	@JsonProperty("presenca")
	private Boolean presenca;

	@JsonProperty("nomeAula")
	private String nomeAula;

	public boolean isPresenca() {
		return presenca;
	}

	public Long getIdAula() {
		return idAula;
	}

	public void setIdAula(Long idAula) {
		this.idAula = idAula;
	}

	public String getDataAula() {
		return dataAula;
	}

	public void setDataAula(String dataAula) {
		this.dataAula = dataAula;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public Boolean getPresenca() {
		return presenca;
	}

	public void setPresenca(Boolean presenca) {
		this.presenca = presenca;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNomeAula() {
		return nomeAula;
	}

	public void setNomeAula(String nomeAula) {
		this.nomeAula = nomeAula;
	}
}
