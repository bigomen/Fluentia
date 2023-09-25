package br.com.fluentia.dto;

import br.com.fluentia.model.Modalidade;

import java.io.Serial;
import java.io.Serializable;

public class ModalidadeDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private Long id;
	private String descricao;

	public Modalidade dtoToModel(){
		Modalidade model = new Modalidade();
		model.setId(this.id);
		model.setDescricao(this.descricao);
		return model;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
