package br.com.fluentia.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthToken {
	private String token;
	private List<String> roles;
	private UserDTO userDto;

	public AuthToken(String token, Collection<? extends GrantedAuthority> collection, UserDTO userDto) {
		this.token = token;
		roles = new ArrayList<>();
		for (GrantedAuthority grantedAuthority : collection) {
			roles.add(grantedAuthority.getAuthority());
		}
		this.userDto = userDto;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
	
	
	
}