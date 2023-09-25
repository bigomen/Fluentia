package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PagSeguroCobrancaDto {

    @JsonProperty("reference_id")
    private String id;

    @JsonProperty("description")
    private final String descricao = "Compra de créditos";

    @JsonProperty("qtd_creditos")
    private int qtdCreditos;

    @JsonProperty("amount")
    private ValorCobrancaDto valor;

    @JsonProperty("payment_method")
    private MetodoPagamentoDto metodoPagamentoDto;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQtdCreditos() {
		return qtdCreditos;
	}

	public void setQtdCreditos(int qtdCreditos) {
		this.qtdCreditos = qtdCreditos;
	}

	public ValorCobrancaDto getValor() {
		return valor;
	}

	public void setValor(ValorCobrancaDto valor) {
		this.valor = valor;
	}

	public MetodoPagamentoDto getMetodoPagamentoDto() {
		return metodoPagamentoDto;
	}

	public void setMetodoPagamentoDto(MetodoPagamentoDto metodoPagamentoDto) {
		this.metodoPagamentoDto = metodoPagamentoDto;
	}

	public String getDescricao() {
		return descricao;
	}
    
    
}
