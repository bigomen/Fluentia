package br.com.fluentia.service;

import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.Professor;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.ProfessorRepository;
import br.com.fluentia.security.dao.UserDAO;
import br.com.fluentia.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private UserDAO userRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	
	protected RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	protected Aluno getAlunoByTokenLogged() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Aluno> aluno = alunoRepository.findByEmail(email);
		if(aluno.isEmpty()) throw new NotFoundException("Aluno");
		return aluno.get();
	}

	protected Aluno verificarAluno() {
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Aluno> aluno = alunoRepository.findByEmail(email);
		return aluno.orElse(null);
	}
	
	public br.com.fluentia.security.model.User getUser() {
		var username = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userRepository.findByEmail((String) username);
	}

	public User verificaAdmin(){
		String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User admin = userRepository.findByEmail(username);
		if(Objects.nonNull(admin)){
			return admin;
		}
		return null;
	}

	public Professor getProfessor(){
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Professor> professor = professorRepository.findByEmail(email);
		if(professor.isEmpty()) throw new NotFoundException("Professor");
		return professor.get();
	}
	public Professor verificarProfessor(){
		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<Professor> professor = professorRepository.findByEmail(email);
		return professor.orElse(null);
	}
}
