package br.com.fluentia.security.dto;

import br.com.fluentia.security.model.Role;
import br.com.fluentia.security.model.User;

import java.util.Set;


public class UserDTO {

	private long id;
	private String email;
	private String nome;
	private String senha;
	private Set<Role> roles;

	public User dtoToModel(){
		User model = new User();
		model.setId(this.id);
		model.setEmail(this.email);
		model.setNome(this.nome);
		model.setSenha(this.senha);
		return model;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}