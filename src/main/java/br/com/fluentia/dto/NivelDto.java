package br.com.fluentia.dto;

import br.com.fluentia.model.Nivel;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

public class NivelDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;
	private Integer ativo;

	@NotEmpty(message = "Nome")
	private String nome;
	private Integer quantidadeHoras;

	public Nivel dtoToModel(){
		Nivel model = new Nivel();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setNome(this.nome);
		model.setQuantidadeHoras(this.quantidadeHoras);
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getQuantidadeHoras() {
		return quantidadeHoras;
	}

	public void setQuantidadeHoras(Integer quantidadeHoras) {
		this.quantidadeHoras = quantidadeHoras;
	}
}
