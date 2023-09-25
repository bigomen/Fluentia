package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PagSeguroValorPlanoDto {

    @JsonProperty("amountPerPayment")
    private String amountPerPayment;

    @JsonProperty("updateSubscriptions")
    private final Boolean updateSubscriptions = false;

	public String getAmountPerPayment() {
		return amountPerPayment;
	}

	public void setAmountPerPayment(String amountPerPayment) {
		this.amountPerPayment = amountPerPayment;
	}

	public Boolean getUpdateSubscriptions() {
		return updateSubscriptions;
	}
    
    
}