package br.com.fluentia.model;

import br.com.fluentia.dto.NivelDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="nivel_aluno_aula")
public class NivelAlunoAula implements Serializable {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,nullable = false)
	private String nome;
	@Column(nullable = true)
	private Integer livre;
	@Column
	private Integer ativo;
	@Column
	private Integer qtde;

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
	
	public void dtoToModel (NivelDto dto) {
		this.id = dto.getId();
		this.ativo = dto.getAtivo();
		this.nome = dto.getNome();
	}
	public NivelDto modelToDto () {
		NivelDto dto = new NivelDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setNome(this.nome);
		return dto;
	}

	public Integer getLivre() {
		return livre;
	}

	public void setLivre(Integer livre) {
		this.livre = livre;
	}

	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
	
	
	
}
