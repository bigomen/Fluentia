package br.com.fluentia.service;

import br.com.fluentia.dto.TipoAulaDto;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.TipoAula;
import br.com.fluentia.repository.TipoAulaRepository;
import br.com.fluentia.type.StatusTipoAulaEnum;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TipoAulaService extends AbstractService {

	@Autowired
	private TipoAulaRepository tipoAulaRepository;

	public void criar(TipoAulaDto dto){
		if(Objects.nonNull(dto.getId())){
			if(!tipoAulaRepository.existsById(dto.getId())) throw new NotFoundException("Tipo Aula");
			if(tipoAulaRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
			dto.setAtivo(tipoAulaRepository.statusTipoAula(dto.getId()));
		}else {
			if(tipoAulaRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
		}
		tipoAulaRepository.save(dto.dtoToModel());
	}

	@Transactional
	public void ativar(Long idTipoAula){
		if(!tipoAulaRepository.existsById(idTipoAula)) throw new NotFoundException("Tipo aula");
		tipoAulaRepository.alterarStatus(StatusTipoAulaEnum.ATIVO.getStatus(), idTipoAula);
	}

	@Transactional
	public void desativar(Long idTipoAula){
		if(!tipoAulaRepository.existsById(idTipoAula)) throw new NotFoundException("Tipo aula");
		tipoAulaRepository.alterarStatus(StatusTipoAulaEnum.INATIVO.getStatus(), idTipoAula);
	}
	
	public List<TipoAulaDto> buscarTodos() {
		List<TipoAula> listTipoAula = tipoAulaRepository.findAllByOrderByNome();
		return listTipoAula.stream().map(TipoAula::modelToDto).collect(Collectors.toList());
	}

	public List<TipoAulaDto> buscarByStatus(Integer status) {
		List<TipoAula> listTipoAula = tipoAulaRepository.findByStatus(status);
		List<TipoAulaDto> tipoAulaDto = new ArrayList<>();
		for (TipoAula tipoAula : listTipoAula) {
			tipoAulaDto.add(tipoAula.modelToDto());
		}
		return tipoAulaDto;
	}

	private void validarFront(TipoAulaDto dto) throws HttpException {
		if (dto == null || dto.getNome().isBlank() || dto.getNome().isEmpty()) {
			throw new HttpException("Existe(m) campos não preenchido(s)");
		}

	}

	public List<TipoAulaDto> buscarAtivos() {
		List<TipoAula> tipoAulas = tipoAulaRepository.findAllByAtivoOrderByNome(StatusTipoAulaEnum.ATIVO.getStatus());
		return tipoAulas.stream().map(TipoAula::modelToDto).collect(Collectors.toList());
	}

}
