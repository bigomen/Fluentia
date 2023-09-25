package br.com.fluentia.dto;

import br.com.fluentia.model.PreMatricula;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;

public class PreMatriculaDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

    @JsonProperty(value = "id")
    private Long id;

    @NotEmpty(message = "Nome")
    @JsonProperty(value = "nome")
    private String nome;

    @NotEmpty(message = "Email")
    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "celular")
    private String celular;

	@NotNull(message = "Empresa")
	@JsonProperty(value = "idEmpresa")
	private Long idEmpresa;

	@JsonProperty(value = "empresa")
	private EmpresaDto empresa;

	public PreMatriculaDto() {
	}

	public PreMatricula dtoToModel () {
		PreMatricula model = new PreMatricula();
		model.setId(this.id);
		model.setNome(this.nome);
		model.setEmail(this.email);
		model.setCelular(this.celular);
		model.setEmpresa(null);
		model.setIdEmpresa(this.idEmpresa);
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

	public Long getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public EmpresaDto getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaDto empresa) {
		this.empresa = empresa;
	}
}
