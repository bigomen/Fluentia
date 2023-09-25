package br.com.fluentia.security.controller;

import br.com.fluentia.model.Professor;
import br.com.fluentia.security.dto.UserDTO;
import br.com.fluentia.security.jwt.TokenAuthenticationService;
import br.com.fluentia.security.model.Role;
import br.com.fluentia.security.service.UserService;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/authenticate")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private Environment env;
	
//	@GetMapping(value = "/user/find-data")
//	public UserDTO buscarDadosDoUsuarioLogado() {
//		return userService.buscarDadosDoUsuarioLogado();
//	}

//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public User saveUser(@RequestBody UserDTO user) {
//		return userService.save(user);
//	}
//
//	@RequestMapping(value = "/user/all", method = RequestMethod.GET)
//	public List<User> findAll(){
//		return userService.findAll();
//	}
//
//	@RequestMapping(value = "/user/user", method = RequestMethod.GET)
//	public User findUser(){
//		return userService.findUser();
//	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/forgot")
	public void forgot(@RequestBody Map<String,String> map) throws HttpException {
		userService.forgot(map);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> login(@RequestBody Map<String,String> map) throws HttpException {
		var user = userService.login(map.get("username"), map.get("password"));
		if (user == null)
	        return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
	
		TokenAuthenticationService tks = new TokenAuthenticationService(env);
		
		List<String> rolesGrupo = new ArrayList<>();
		for (Role role : user.getRoles()) {
			rolesGrupo.add(role.getName());
		}
		
		
        String tokenAuth = tks.generateToken(user.getEmail(), rolesGrupo, user.getNome(), user.getId(), Long.parseLong(env.getProperty("jwt.token.validity")), env.getProperty("jwt.signing.key"));
        String tokenRefresh = tks.generateToken(user.getEmail(), rolesGrupo, user.getNome(), user.getId(), Long.parseLong(env.getProperty("jwt.token.refresh.validity")), env.getProperty("jwt.signing.key"));
        	
        tks.updateContext(user.getEmail(), rolesGrupo, user.getId());
        
        HttpHeaders header = new HttpHeaders();
        header.add(TokenAuthenticationService.HEADER_AUTHORIZATION, tokenAuth);
        header.add(TokenAuthenticationService.HEADER_REFRESH, tokenRefresh);

        UserDTO userDto = new UserDTO();
        userDto.setNome(user.getNome());
        userDto.setEmail(user.getEmail());
        if (user.getRoles().size() > 0) {
        	HashSet<Role> roleSet = new HashSet<>();
        	roleSet.add((Role)user.getRoles().toArray()[0]);
        	userDto.setRoles(roleSet);
        }
        return new ResponseEntity<>(userDto, header, HttpStatus.OK);
		
		
	}

	@RequestMapping(value = "/refresh", method = RequestMethod.POST)
	public ResponseEntity<Object> refresh() throws HttpException {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@PostMapping("/change")
	public void change(@RequestBody Map<String,String> map){
		userService.changePassword(map);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/admin/removerEspeciaisCpf")
	public void removerEspeciaisCpf(){
		userService.removerEspeciaisCpf();
	}
}