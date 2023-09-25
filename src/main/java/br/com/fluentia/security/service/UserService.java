package br.com.fluentia.security.service;

import br.com.fluentia.security.model.User;
import org.apache.http.HttpException;

import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
    User getUser();
    void forgot(Map<String, String> map) throws HttpException ;
	void changePassword(Map<String, String> map);
	
	User login(String username, String password);

    void removerEspeciaisCpf();

	
}