package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AdesaoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("plan")
	private String codigoPlano;

	@JsonProperty("hash")
	private String hash;

	@JsonProperty("paymentMethod")
	private FormaPagamentoDto formaPagamentoDto;

	public String getCodigoPlano() {
		return codigoPlano;
	}

	public void setCodigoPlano(String codigoPlano) {
		this.codigoPlano = codigoPlano;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public FormaPagamentoDto getFormaPagamentoDto() {
		return formaPagamentoDto;
	}

	public void setFormaPagamentoDto(FormaPagamentoDto formaPagamentoDto) {
		this.formaPagamentoDto = formaPagamentoDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
