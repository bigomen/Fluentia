package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class MotivoCancelamentoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("motivo")
	private String motivo;

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
}