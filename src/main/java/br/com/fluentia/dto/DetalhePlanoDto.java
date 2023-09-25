package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class DetalhePlanoDto {

    @JsonProperty("name")
    private String nome;

    @JsonProperty("charge")
    private final String tipoCobranca= "AUTO";

    @JsonProperty("period")
    private final String periodicidade = "MONTHLY";

    @JsonProperty("amountPerPayment")
    private String valor;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getTipoCobranca() {
		return tipoCobranca;
	}

	public String getPeriodicidade() {
		return periodicidade;
	}
    
    
}