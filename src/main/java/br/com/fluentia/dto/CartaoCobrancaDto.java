package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CartaoCobrancaDto {

	@JsonProperty("number")
	private String numero;

	@JsonProperty("exp_month")
	private int mes;

	@JsonProperty("exp_year")
	private int ano;

	@JsonProperty("security_code")
	private String cvv;

	@JsonProperty("holder")
	private TitularCartaoDto titularCartaoDto;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public TitularCartaoDto getTitularCartaoDto() {
		return titularCartaoDto;
	}

	public void setTitularCartaoDto(TitularCartaoDto titularCartaoDto) {
		this.titularCartaoDto = titularCartaoDto;
	}
	
	
}