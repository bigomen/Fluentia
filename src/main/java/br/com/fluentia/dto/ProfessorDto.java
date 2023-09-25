package br.com.fluentia.dto;

import br.com.fluentia.model.Material;
import br.com.fluentia.model.Professor;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Nome")
	private String nome;
	private String celular;

	@NotEmpty(message = "Email")
	private String email;

	@NotEmpty(message = "CPF")
	private String cpf;
	private LocalDate dataNascimento;
	private Integer ativo;
	private String idZoom;

	private List<MaterialDto> materialList;

	public Professor dtoToModel () {
		Professor model = new Professor();
		model.setId(this.id);
		model.setAtivo(this.ativo);
		model.setCelular(this.celular);
		model.setCpf(this.cpf);
		model.setDataNascimento(this.dataNascimento);
		model.setEmail(this.email);
		model.setNome(this.nome);
		model.setIdZoom(this.idZoom);
		if(this.materialList != null){
			List<Material> materials = new ArrayList<>();
			for(MaterialDto m : this.materialList){
				Material material = new Material();
				material.setIdProfessor(this.id);
				material.setId(m.getId());
				material.setUrl(m.getUrl());
				material.setDescricao(m.getDescricao());
				materials.add(material);
			}
			model.setMaterialList(materials);
		}
		return model;
	}

	public String getIdZoom() {
		return idZoom;
	}
	public void setIdZoom(String idZoom) {
		this.idZoom = idZoom;
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
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Integer getAtivo() {
		return ativo;
	}
	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
	}

	public List<MaterialDto> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialDto> materialList) {
		this.materialList = materialList;
	}
}
