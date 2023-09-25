package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;


public class ValorCobrancaDto {

    final BigDecimal valorCredito = new BigDecimal(37.00);

    @JsonProperty("value")
    private int valor;

    @JsonProperty("currency")
    private final String moeda = "BRL";

    public ValorCobrancaDto(int qtdCredito) {
        this.valor = valorCredito.multiply(new BigDecimal(qtdCredito * 100)).intValue();
    }

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public BigDecimal getValorCredito() {
		return valorCredito;
	}

	public String getMoeda() {
		return moeda;
	}
    
    
    
}
