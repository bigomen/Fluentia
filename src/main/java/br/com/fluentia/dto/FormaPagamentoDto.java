package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;



public class FormaPagamentoDto {

    @JsonProperty("type")
    private final String meioPagamento = "CREDITCARD";

    @JsonProperty("creditCard")
    private CartaoDeCreditoDto cartaoDeCreditoDto;

	public CartaoDeCreditoDto getCartaoDeCreditoDto() {
		return cartaoDeCreditoDto;
	}

	public void setCartaoDeCreditoDto(CartaoDeCreditoDto cartaoDeCreditoDto) {
		this.cartaoDeCreditoDto = cartaoDeCreditoDto;
	}

	public String getMeioPagamento() {
		return meioPagamento;
	}
    
    
}