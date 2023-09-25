package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PagSeguroPlanoDto {
    @JsonProperty("reference")
    private String referencia;

    @JsonProperty("preApproval")
    private DetalhePlanoDto detalhePlanoDto;

    @JsonProperty("receiver")
    private ReceptorDto receptorDto;

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public DetalhePlanoDto getDetalhePlanoDto() {
		return detalhePlanoDto;
	}

	public void setDetalhePlanoDto(DetalhePlanoDto detalhePlanoDto) {
		this.detalhePlanoDto = detalhePlanoDto;
	}

	public ReceptorDto getReceptorDto() {
		return receptorDto;
	}

	public void setReceptorDto(ReceptorDto receptorDto) {
		this.receptorDto = receptorDto;
	}
    
    
}
