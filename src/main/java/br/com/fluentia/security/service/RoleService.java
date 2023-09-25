package br.com.fluentia.security.service;

import br.com.fluentia.security.model.Role;

public interface RoleService {
	Role findByName(String name);
}
