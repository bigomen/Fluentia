package br.com.fluentia.service;

import br.com.fluentia.dto.TemaDto;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.Tema;
import br.com.fluentia.repository.TemaRepository;
import br.com.fluentia.type.StatusTemaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TemaService extends AbstractService {

	@Autowired
	private TemaRepository temaRepository;

	public void criar(TemaDto dto){
		if(Objects.nonNull(dto.getId())){
			if(!temaRepository.existsById(dto.getId())) throw new NotFoundException("Tema");
			if(temaRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
			dto.setAtivo(temaRepository.statusTema(dto.getId()));
		}else {
			if(temaRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
		}
		temaRepository.save(dto.dtoToModel());
	}

	@Transactional
	public void desativar(Long idTema){
		if(!temaRepository.existsById(idTema)) throw new NotFoundException("Tema");
		temaRepository.alterarStatus(idTema, StatusTemaEnum.INATIVO.getStatus());
	}

	@Transactional
	public void ativar(Long idTema){
		if(!temaRepository.existsById(idTema)) throw new NotFoundException("Tema");
		temaRepository.alterarStatus(idTema, StatusTemaEnum.ATIVO.getStatus());
	}
	
	public List<TemaDto> lista() {
		List<Tema> temas = temaRepository.findAll(Sort.by("nome").ascending());
		return temas.stream().map(Tema::modelToDto).collect(Collectors.toList());
	}

	public List<TemaDto> listaAtivos() {
		List<Tema> temas = temaRepository.listaPorStatus(StatusTemaEnum.ATIVO.getStatus());
		return temas.stream().map(Tema::modelToDto).collect(Collectors.toList());
	}
}
