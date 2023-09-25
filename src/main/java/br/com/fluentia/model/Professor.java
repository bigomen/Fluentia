package br.com.fluentia.model;

import br.com.fluentia.dto.MaterialDto;
import br.com.fluentia.dto.ProfessorDto;
import br.com.fluentia.type.StatusProfessorEnum;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="professor")
public class Professor implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false,unique = true)
	private String nome;
	@Column
	private String celular;
	@Column(unique = true,nullable = false)
	private String email;
	@Column(unique = true,nullable = false)
	private String cpf;
	@Column(name="dt_nasc")
	private LocalDate dataNascimento;
	@Column
	private Integer ativo;
	@Column(name="id_zoom")
	private String idZoom;

	@Column(name = "senha")
	private String senha;

	@Column(name = "reset_token")
	private String resetToken;

	@OneToMany(mappedBy = "professor", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Material> materialList;

	public Professor() {
	}

	public Professor(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Professor(Long id, String nome, String email) {
		this.id = id;
		this.nome = nome;
		this.email = email;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	@PrePersist
	private void prePersist(){
		this.ativo = StatusProfessorEnum.ATIVO.getStatus();
	}

	public ProfessorDto modelToDto () {
		ProfessorDto dto = new ProfessorDto();
		dto.setId(this.id);
		dto.setAtivo(this.ativo);
		dto.setCelular(this.celular);
		dto.setCpf(this.cpf);
		dto.setDataNascimento(this.dataNascimento);
		dto.setEmail(this.email);
		dto.setNome(this.nome);
		dto.setIdZoom(this.idZoom);
		if(this.materialList != null){
			List<MaterialDto> materialDtos = new ArrayList<>();
			for(Material m : this.materialList){
				MaterialDto material = new MaterialDto();
				material.setIdProfessor(this.getId());
				material.setId(m.getId());
				material.setDescricao(m.getDescricao());
				material.setUrl(m.getUrl());
				materialDtos.add(material);
			}
			dto.setMaterialList(materialDtos);
		}
		return dto;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}
}
