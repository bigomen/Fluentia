package br.com.fluentia.model;

import br.com.fluentia.dto.TipoAulaDto;
import br.com.fluentia.type.StatusTipoAulaEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="tipo_aula")
public class TipoAula implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	public static final Long PRIVATE_CLASS = 16L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true,nullable = false)
	private String nome;
	@Column
	private Integer ativo;
	@Column
	private Integer qtde;

	@PrePersist
	private void prePersist(){
		this.ativo = StatusTipoAulaEnum.ATIVO.getStatus();
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

	public TipoAulaDto modelToDto () {
		TipoAulaDto dto = new TipoAulaDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setNome(this.nome);
		dto.setQtde(this.qtde);
		return dto;
	}


	public Integer getQtde() {
		return qtde;
	}

	public void setQtde(Integer qtde) {
		this.qtde = qtde;
	}
}
