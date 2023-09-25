package br.com.fluentia.service;

import br.com.fluentia.dto.EmailDto;
import br.com.fluentia.dto.MaterialDto;
import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.dto.ProfessorDto;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.Professor;
import br.com.fluentia.repository.ProfessorRepository;
import br.com.fluentia.security.dao.UserDAO;
import br.com.fluentia.security.service.RoleService;
import br.com.fluentia.type.StatusProfessorEnum;
import br.com.fluentia.utils.GeneratePageable;
import br.com.fluentia.utils.GeneratePasswordUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProfessorService extends AbstractService {

	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private RoleService roleService;
	@Autowired 
	BCryptPasswordEncoder bcrypt;
	@Autowired
	private UserDAO userRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ZoomService zoomService;

	public void criar(ProfessorDto dto){
		if(professorRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
		if(professorRepository.existsByEmail(dto.getEmail())) throw new UniqueException("Email");
		if(professorRepository.existsByCpf(dto.getCpf())) throw new UniqueException("CPF");
		Set<String> descricoes = dto.getMaterialList().stream().map(MaterialDto::getDescricao).collect(Collectors.toSet());
		if(descricoes.size() != dto.getMaterialList().size()){
			throw new BusinessException("Materiais com nomes iguais");
		}
		Professor professor = dto.dtoToModel();
		String passwordGerado = GeneratePasswordUtils.gerarSenha();
		professor.setSenha(GeneratePasswordUtils.crypt(passwordGerado));
		professorRepository.save(professor);
		EmailDto dtoEmail = new EmailDto();
		dtoEmail.setTitulo("Nova senha");
		dtoEmail.setEmail(professor.getEmail());
		dtoEmail.setTexto("{\"password\":\"" + passwordGerado + "\"}");
		try {
			emailService.sendMail(dtoEmail);
		}catch (UnirestException e){
			throw new InternalErroException();
		}
	}

	public void atualizar(ProfessorDto dto){
		Optional<Professor> professor = professorRepository.findById(dto.getId());
		if(professor.isEmpty()) throw new NotFoundException("Professor");
		if(professorRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
		Set<String> descricoes = dto.getMaterialList().stream().map(MaterialDto::getDescricao).collect(Collectors.toSet());
		if(descricoes.size() != dto.getMaterialList().size()){
			throw new BusinessException("Materiais com nomes iguais");
		}
		Professor model = dto.dtoToModel();
		model.setAtivo(professor.get().getAtivo());
		model.setSenha(professor.get().getSenha());
		model.setResetToken(professor.get().getResetToken());
		professorRepository.save(model);
	}

	@Transactional
	public void desativar(Long idProfessor){
		if(!professorRepository.existsById(idProfessor)) throw new NotFoundException("Professor");
		professorRepository.alterarStatus(idProfessor, StatusProfessorEnum.INATIVO.getStatus());
	}

	@Transactional
	public void ativar(Long idProfessor){
		if(!professorRepository.existsById(idProfessor)) throw new NotFoundException("Professor");
		professorRepository.alterarStatus(idProfessor, StatusProfessorEnum.ATIVO.getStatus());
	}

	public Page<ProfessorDto> lista(PaginacaoDto params) {
		Page<Professor> professores = professorRepository.findAll(GeneratePageable.build(params, Sort.by("nome").ascending()));
		return professores.map(Professor::modelToDto);
	}

	public List<ProfessorDto> buscarByStatus(Integer status) {
		List<Professor> professores = professorRepository.findByStatus(status);
		List<ProfessorDto> listDto = new ArrayList<>();
		for (Professor professor : professores) {
			listDto.add(professor.modelToDto());
		}
		return listDto;
	}

	private void validarFront(ProfessorDto dto) throws HttpException {
		if (dto == null || dto.getNome().isBlank() || dto.getNome().isEmpty() || dto.getEmail().isBlank()
				|| dto.getEmail().isEmpty() || dto.getCpf().isBlank() || dto.getCpf().isEmpty()) {
			throw new HttpException("Existe(m) campos não preenchido(s)");
		}

	}
	
	public ProfessorDto buscarPorCpf(String cpf){
		Optional<Professor> professor = professorRepository.findByCpf(cpf);
		if(professor.isEmpty()) throw new NotFoundException("Professor");
		return professor.get().modelToDto();
	}

	public List<ProfessorDto> listaAtivos() {
		List<Professor> professores = professorRepository.listaPorStatus(StatusProfessorEnum.ATIVO.getStatus());
		return professores.stream().map(Professor::modelToDto).collect(Collectors.toList());
	}
	
//	@Transactional
//	public Long gerarLogin (ProfessorDto dto) {
//		var senhaGerada = GeneratePasswordUtils.gerarSenha();
//
//		var professor = professorRepository.getById(dto.getId());
//
//		Optional<User> optionalUser = null;
//		if(professor.getIdUser()!=null) {
//			optionalUser = userRepository.findById(professor.getIdUser());
//		}
//		User user = null;
//		if(optionalUser!=null
//				&& optionalUser.isPresent()) {
//			user = optionalUser.get();
//			user.setPassword(bcrypt.encode(senhaGerada));
//		}else {
//			user = new User();
//			user.setName(professor.getNome());
//			user.setUsername(professor.getEmail());
//			user.setPhone(professor.getCelular());
//			user.setEmail(professor.getEmail());
//			user.setPassword(bcrypt.encode(senhaGerada));
//			var role = roleService.findByName("PROFESSOR");
//			user.setRoles(Set.of(role));
//		}
//
//		user = userRepository.save(user);
//		professor.setIdUser(user.getId());
//		professor = professorRepository.save(professor);
//		try {
//			this.enviarEmail(professor,senhaGerada);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (UnirestException e) {
//			e.printStackTrace();
//		}
//
//		return professor.getId();
//	}

	public ProfessorDto logado() {
		Professor professor = this.getProfessor();
		return professor.modelToDto();
	}

	public String buscarIdZoom(Long idProfessor) {
		if(!professorRepository.existsById(idProfessor)) throw new NotFoundException("Professor");
		return professorRepository.getIdZoom(idProfessor);
	}

	public String listaZoomTeste(String profMail){
		Map response = zoomService.findUsers();
		List<Map> zoomUsers = (List<Map>) response.get("users");
		for(Map m : zoomUsers){
			String userEmail = (String) m.get("email");
			if(Objects.equals(userEmail, profMail)) return (String) m.get("id");
		}
		return null;
	}
}
