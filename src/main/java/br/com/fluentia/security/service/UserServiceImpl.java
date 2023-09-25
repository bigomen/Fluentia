package br.com.fluentia.security.service;

import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.Professor;
import br.com.fluentia.repository.AdesaoRepository;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.ProfessorRepository;
import br.com.fluentia.security.dao.UserDAO;
import br.com.fluentia.security.model.Role;
import br.com.fluentia.security.model.User;
import br.com.fluentia.service.AbstractService;
import br.com.fluentia.service.EmailService;
import br.com.fluentia.utils.GeneratePasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service(value = "userService")
public class UserServiceImpl extends AbstractService implements UserService {

	@Autowired
	private AdesaoRepository adesaoRepository;

	@Autowired
	private UserDAO userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private RoleService roleService;

	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private EmailService emailService;

	@Override
	public Optional<User> findById(Long id) {
		return userDao.findById(id);
	}

	public User findUser() {
		var user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDao.findByEmail((String) user);
	}

	@Override
	@Transactional
	public void forgot(Map<String, String> map) {
		String email = map.get("email");
		String cpf = map.get("cpf");
		String novaSenha = GeneratePasswordUtils.gerarSenha();
		String novaSenhaCrypt = GeneratePasswordUtils.crypt(novaSenha);
		User admin = userDao.findByEmail(email);
		Optional<Professor> professor = professorRepository.findByEmailAndCpf(email, cpf);
		Optional<Aluno> aluno = alunoRepository.findByEmailAndCpf(email, cpf);
		if(Objects.nonNull(admin)){
			userDao.atualizarSenha(novaSenhaCrypt, admin.getId());
		}else if(professor.isPresent()){
			professorRepository.atualizarSenha(novaSenhaCrypt, professor.get().getId());
		}else if(aluno.isPresent()){
			alunoRepository.atualizarSenha(novaSenhaCrypt, aluno.get().getId());
		}else {
			throw new NotFoundException("Usuário");
		}
		try {
			emailService.enviarEmail(email, novaSenha);
		}catch (Exception e){
			throw new BusinessException("Erro ao enviar email");
		}
	}



	private void validateProfessor(Professor professor, User user) {
//		if(user.getId()==professor.getIdUser()) {
//			var userFinded = userDao.findById(user.getId()).get();
//			var password = GeneratePasswordUtils.gerarSenha();
//
//			userFinded.setPassword(GeneratePasswordUtils.crypt(password));
//			userDao.save(userFinded);
//
//			try {
//				professorService.enviarEmail(professor,password);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnirestException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	private void validateAluno(Aluno aluno, User user) {
//		if(user.getId()==aluno.getIdUser()) {
//			var userFinded = userDao.findById(user.getId()).get();
//			var password = GeneratePasswordUtils.gerarSenha();
//
//			userFinded.setPassword(GeneratePasswordUtils.crypt(password));
//			userDao.save(userFinded);
//			try {
//				alunoService.enviarEmail(aluno,password);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UnirestException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

	}

	@Override
	@Transactional
	public void changePassword(Map<String, String> map){

		String passwordConfirmacao = map.get("passwordConfirmacao");
		String passwordNew = map.get("passwordNew");

		String passCrypt = GeneratePasswordUtils.crypt(passwordNew);

		if(!passwordConfirmacao.equals(passwordNew)
				|| passwordConfirmacao.length()<6) {
			throw new BusinessException("As senhas digitadas não são iguais ou possuem menos de 6 caracteres");
		}

		User admin = this.verificaAdmin();
		Professor professor = this.verificarProfessor();
		Aluno aluno = this.verificarAluno();

		if(Objects.nonNull(admin)){
			userDao.atualizarSenha(passCrypt, admin.getId());
		}else if(Objects.nonNull(professor)){
			professorRepository.atualizarSenha(passCrypt, professor.getId());
		}else if(Objects.nonNull(aluno)){
			alunoRepository.atualizarSenha(passCrypt, aluno.getId());
		}else {
			throw new NotFoundException("Erro ao verificar usuário");
		}
	}

	@Override
	public User login(String username, String password) {

		User user = userDao.findByEmail(username);
		if (user != null && bCryptPasswordEncoder.matches(password, user.getSenha())) {
			user.getRoles().clear();
			user.getRoles().add(userDao.getAdminRole());
			return user;
		}

		Optional<Professor> professor = professorRepository.findByEmail(username);
		if(professor.isPresent() && bCryptPasswordEncoder.matches(password, professor.get().getSenha())){
			User profUser = new User();
			profUser.setEmail(professor.get().getEmail());
			profUser.setNome(professor.get().getNome());
			profUser.setId(professor.get().getId());
			Role profRole = professorRepository.getRoles();
			Set<Role> profRoles = new HashSet<>();
			profRoles.add(profRole);
			profUser.setRoles(profRoles);
			return profUser;
		}

		Optional<Aluno> aluno = alunoRepository.findByEmail(username);
		if(aluno.isPresent() && bCryptPasswordEncoder.matches(password, aluno.get().getSenha())){
			User alunoUser = new User();
			alunoUser.setEmail(aluno.get().getEmail());
			alunoUser.setNome(aluno.get().getNome());
			alunoUser.setId(aluno.get().getId());
			Role alunoRole = alunoRepository.getRoles();
			Set<Role> alunoRoles = new HashSet<>();
			alunoRoles.add(alunoRole);
			alunoUser.setRoles(alunoRoles);
			return alunoUser;
		}
		
		return null;
	}

	@Override
	@Transactional
	public void removerEspeciaisCpf(){
		Map<String, List<Long>> erros = new HashMap<>();
		erros.put("professores", ajustaProfessor());
		erros.put("alunos", ajustaAluno());
		System.out.println(erros);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private List<Long> ajustaProfessor(){
		List<Long> ids = new ArrayList<>();
		List<Professor> professors = professorRepository.getIdAndCpf();
		if(!professors.isEmpty()){
			for(Professor p : professors){
				String cpfLimpo = StringUtils.replace(p.getNome(), ".", "");
				cpfLimpo = StringUtils.replace(cpfLimpo, "-", "");
				try {
					professorRepository.atualizarCpf(cpfLimpo, p.getId());
				}catch (Exception e){
					ids.add(p.getId());
				}
			}
		}
		return ids;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private List<Long> ajustaAluno(){
		List<Long> ids = new ArrayList<>();
		List<Aluno> alunos = alunoRepository.getIdAndCpf();
		if(!alunos.isEmpty()){
			for(Aluno p : alunos){
				String cpfLimpo = StringUtils.replace(p.getNome(), ".", "");
				cpfLimpo = StringUtils.replace(cpfLimpo, "-", "");
				try {
					alunoRepository.atualizaCpf(cpfLimpo, p.getId());
				}catch (Exception e){
					ids.add(p.getId());
				}
			}
		}
		return ids;
	}
}