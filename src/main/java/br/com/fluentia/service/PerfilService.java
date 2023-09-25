package br.com.fluentia.service;

import br.com.fluentia.dto.PerfilDto;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.Perfil;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.PerfilRepository;
import br.com.fluentia.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService extends AbstractService {

	@Autowired
	private PerfilRepository perfilRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private AlunoRepository alunoRepository;

	public void salvar(PerfilDto dto) {
		if(!alunoRepository.existsById(dto.getIdAluno())) throw new NotFoundException("Aluno");
		if(!professorRepository.existsById(dto.getIdProfessor())) throw new NotFoundException("Professor");
		perfilRepository.save(dto.dtoToModel());
	}

	public PerfilDto perfilAluno(Long idAluno) {
		if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
		Optional<Perfil> perfil = perfilRepository.findByIdAluno(idAluno);
		return perfil.map(Perfil::modelToDto).orElse(null);
	}

}
