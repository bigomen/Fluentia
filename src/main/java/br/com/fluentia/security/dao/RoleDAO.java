package br.com.fluentia.security.dao;

import br.com.fluentia.security.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDAO extends CrudRepository<Role, Long> {
	Role findRoleByName(String name);
}
