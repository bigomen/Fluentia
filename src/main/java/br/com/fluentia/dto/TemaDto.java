package br.com.fluentia.dto;

import br.com.fluentia.model.Tema;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

public class TemaDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Nome")
	private String nome;
	private Integer ativo;
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
	public Integer getAtivo() {
		return ativo;
	}
	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public Tema dtoToModel () {
		Tema model = new Tema();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setNome(this.nome);
		return model;
	}
	
}
