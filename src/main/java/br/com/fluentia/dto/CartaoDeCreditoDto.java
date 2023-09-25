package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoDeCreditoDto {

    @JsonProperty("token")
    private String token;

    @JsonProperty("cardNumber")
    private String numero;

    @JsonProperty("cardBrand")
    private String bandeira;

    @JsonProperty("cardCvv")
    private String cvv;

    @JsonProperty("cardExpirationMonth")
    private String mes;

    @JsonProperty("cardExpirationYear")
    private String ano;

    @JsonProperty("holder")
    private TitularCartaoDto titularCartaoDto;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.ano = null;
        this.mes = null;
        this.cvv = null;
        this.numero = null;
        this.bandeira = null;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBandeira() {
        return bandeira;
    }

    public void setBandeira(String bandeira) {
        this.bandeira = bandeira;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public TitularCartaoDto getTitularCartaoDto() {
        return titularCartaoDto;
    }

    public void setTitularCartaoDto(TitularCartaoDto titularCartaoDto) {
        this.titularCartaoDto = titularCartaoDto;
    }
    
}