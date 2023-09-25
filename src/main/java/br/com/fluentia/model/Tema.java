package br.com.fluentia.model;

import br.com.fluentia.dto.TemaDto;
import br.com.fluentia.type.StatusTemaEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="tema")
public class Tema implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,nullable = false)
	private String nome;
	@Column
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

	@PrePersist
	private void prePersist(){
		this.ativo = StatusTemaEnum.ATIVO.getStatus();
	}

	public TemaDto modelToDto () {
		TemaDto dto = new TemaDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setNome(this.nome);
		return dto;
	}
	
}
