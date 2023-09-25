package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;


public class RelatorioPresencaComFrequenciaDto implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	@JsonProperty("taxaFrequencia")
	private String taxaFrequencia;

	@JsonProperty("relatorioPresenca")
	private Collection<RelatorioPresencaDto> relatorioPresencaDto;

	public String getTaxaFrequencia() {
		return taxaFrequencia;
	}

	public void setTaxaFrequencia(String taxaFrequencia) {
		this.taxaFrequencia = taxaFrequencia;
	}

	public Collection<RelatorioPresencaDto> getRelatorioPresencaDto() {
		return relatorioPresencaDto;
	}

	public void setRelatorioPresencaDto(Collection<RelatorioPresencaDto> relatorioPresencaDto) {
		this.relatorioPresencaDto = relatorioPresencaDto;
	}
	
	
}
