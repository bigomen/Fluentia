package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class PagamentoDto {

    @JsonProperty("sessionId")
    private String sessao;

    @JsonProperty("amount")
    private double valor;

    @JsonUnwrapped
    private CartaoDeCreditoDto cartao;

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public CartaoDeCreditoDto getCartao() {
        return cartao;
    }

    public void setCartao(CartaoDeCreditoDto cartao) {
        this.cartao = cartao;
    }
}
