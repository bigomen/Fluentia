package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class MetodoPagamentoDto {

    @JsonProperty("type")
    private final String meioPagamento = "CREDIT_CARD";

    @JsonProperty("installments")
    private int parcelas;

    @JsonProperty("capture")
    private final Boolean captura = true;

    @JsonProperty("card")
    private CartaoCobrancaDto cartaoCobrancaDto;

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public CartaoCobrancaDto getCartaoCobrancaDto() {
		return cartaoCobrancaDto;
	}

	public void setCartaoCobrancaDto(CartaoCobrancaDto cartaoCobrancaDto) {
		this.cartaoCobrancaDto = cartaoCobrancaDto;
	}

	public String getMeioPagamento() {
		return meioPagamento;
	}

	public Boolean getCaptura() {
		return captura;
	}
    
    
}