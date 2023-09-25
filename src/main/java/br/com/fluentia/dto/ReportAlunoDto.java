package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;


public class ReportAlunoDto implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@JsonProperty("nivelSubnivelInicial")
	private String nivelSubnivelInicial;

	@JsonProperty("nivelSubnivelAtual")
	private String nivelSubnivelAtual;

	@JsonProperty("porcentagemAtual")
	private String porcentagemAtual;

	@JsonProperty("aula")
	private String aula;

	public String getNivelSubnivelInicial() {
		return nivelSubnivelInicial;
	}

	public void setNivelSubnivelInicial(String nivelSubnivelInicial) {
		this.nivelSubnivelInicial = nivelSubnivelInicial;
	}

	public String getNivelSubnivelAtual() {
		return nivelSubnivelAtual;
	}

	public void setNivelSubnivelAtual(String nivelSubnivelAtual) {
		this.nivelSubnivelAtual = nivelSubnivelAtual;
	}

	public String getPorcentagemAtual() {
		return porcentagemAtual;
	}

	public void setPorcentagemAtual(String porcentagemAtual) {
		this.porcentagemAtual = porcentagemAtual;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}
}
