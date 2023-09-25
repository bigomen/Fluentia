package br.com.fluentia.security.dao;

import br.com.fluentia.security.model.Role;
import br.com.fluentia.security.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User, Long> {

	User findByEmail(String email);

	@Query("select r from Role r where r.id = 3L")
	Role getAdminRole();

	@Modifying
	@Query("update User u set u.senha = :senha where u.id = :id")
	void atualizarSenha(String senha, Long id);
}
