package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Calendar;

public class AdesaoCanceladaDto implements Serializable {
	public AdesaoCanceladaDto(String codigoAdesao, Calendar dataCancelamento, String motivoCancelamento, String aluno,
			String plano) {
		super();
		this.codigoAdesao = codigoAdesao;
		this.dataCancelamento = dataCancelamento;
		this.motivoCancelamento = motivoCancelamento;
		this.aluno = aluno;
		this.plano = plano;
	}

	private static final long serialVersionUID = 1L;

	@JsonProperty("codigo_adesao")
	private String codigoAdesao;

	@JsonProperty("data_cancelamento")
	private Calendar dataCancelamento;

	@JsonProperty("motivo_cancelamento")
	private String motivoCancelamento;

	@JsonProperty("aluno")
	private String aluno;

	@JsonProperty("plano")
	private String plano;

	public String getCodigoAdesao() {
		return codigoAdesao;
	}

	public void setCodigoAdesao(String codigoAdesao) {
		this.codigoAdesao = codigoAdesao;
	}

	public Calendar getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Calendar dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public String getAluno() {
		return aluno;
	}

	public void setAluno(String aluno) {
		this.aluno = aluno;
	}

	public String getPlano() {
		return plano;
	}

	public void setPlano(String plano) {
		this.plano = plano;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
