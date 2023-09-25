package br.com.fluentia.dto;

import br.com.fluentia.model.TipoAula;
import jakarta.validation.constraints.NotEmpty;

public class TipoAulaDto {
	private Long id;

	@NotEmpty(message = "Nome")
	private String nome;
	private Integer ativo;
	private Integer qtde;

	public TipoAula dtoToModel () {
		TipoAula model = new TipoAula();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setNome(this.nome);
		model.setQtde(this.qtde);
		return model;
	}

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


	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
}
