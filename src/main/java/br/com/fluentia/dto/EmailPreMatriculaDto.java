package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class EmailPreMatriculaDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "celular")
    private String celular;

    @JsonProperty(value = "plano")
    private PlanoDto plano;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public PlanoDto getPlano() {
		return plano;
	}

	public void setPlano(PlanoDto plano) {
		this.plano = plano;
	}
    
    
}