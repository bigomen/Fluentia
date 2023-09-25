package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PagSeguroAdesaoDto {

    @JsonProperty("plan")
    private String codigoPlano;

    @JsonProperty("reference")
    private String referencia;

    @JsonProperty("sender")
    private CompradorDto compradorDto;

    @JsonProperty("paymentMethod")
    private FormaPagamentoDto formaPagamentoDto;

	public String getCodigoPlano() {
		return codigoPlano;
	}

	public void setCodigoPlano(String codigoPlano) {
		this.codigoPlano = codigoPlano;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public CompradorDto getCompradorDto() {
		return compradorDto;
	}

	public void setCompradorDto(CompradorDto compradorDto) {
		this.compradorDto = compradorDto;
	}

	public FormaPagamentoDto getFormaPagamentoDto() {
		return formaPagamentoDto;
	}

	public void setFormaPagamentoDto(FormaPagamentoDto formaPagamentoDto) {
		this.formaPagamentoDto = formaPagamentoDto;
	}
    
    
}