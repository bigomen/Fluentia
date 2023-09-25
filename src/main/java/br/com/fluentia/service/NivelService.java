package br.com.fluentia.service;

import br.com.fluentia.dto.NivelDto;
import br.com.fluentia.dto.SubnivelDto;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.AlunoAula;
import br.com.fluentia.model.Nivel;
import br.com.fluentia.repository.AlunoAulaRepository;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.NivelRepository;
import br.com.fluentia.type.NivelAlunoEnum;
import br.com.fluentia.type.StatusNivelEnum;
import br.com.fluentia.type.SubnivelAlunoEnum;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class NivelService extends AbstractService{

	@Autowired
	private NivelRepository nivelRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private AlunoAulaRepository alunoAulaRepository;

	private static final int MIN_MINUTOS_SUBNIVEL_1 = 0;
	private static final int MAX_MINUTOS_SUBNIVEL_1 = 1259;
	private static final int MIN_MINUTOS_SUBNIVEL_2 = 1260;
	private static final int MAX_MINUTOS_SUBNIVEL_2 = 2459;
	private static final int MIN_MINUTOS_SUBNIVEL_3 = 2460;
	private static final int MAX_MINUTOS_SUBNIVEL_3 = 3600;
	
	public void criar(NivelDto dto){
		if(Objects.nonNull(dto.getId())){
			if(!nivelRepository.existsById(dto.getId())) throw new NotFoundException("Nivel");
			if(nivelRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
			dto.setAtivo(nivelRepository.statusNivel(dto.getId()));
		}else {
			if(nivelRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
		}
		nivelRepository.save(dto.dtoToModel());
	}

	public List<NivelDto> buscarAtivos() {
		List<Nivel> niveis = nivelRepository.findAllByAtivoOrderByNomeAsc(StatusNivelEnum.ATIVO.getStatus());
		return niveis.stream().map(Nivel::modelToDto).collect(Collectors.toList());
	}

	@Transactional
	public void desativar(Long idNivel){
		if(!nivelRepository.existsById(idNivel)) throw new NotFoundException("Nivel");
		nivelRepository.alterarStatus(idNivel, StatusNivelEnum.INATIVO.getStatus());
	}

	@Transactional
	public void ativar(Long idNivel){
		if(!nivelRepository.existsById(idNivel)) throw new NotFoundException("Nivel");
		nivelRepository.alterarStatus(idNivel, StatusNivelEnum.ATIVO.getStatus());
	}
	
	public List<NivelDto> lista() {
		List<Nivel> niveis = nivelRepository.findAllByOrderByNomeAsc();
		return niveis.stream().map(Nivel::modelToDto).collect(Collectors.toList());
	}

	public List<SubnivelDto> subnivelDoAluno(Long idAluno){
		if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
		List<AlunoAula> aulas = alunoAulaRepository.getAulasAluno(idAluno);
		if(aulas.isEmpty()){
			throw new BusinessException("Aluno não possui aulas registradas");
		}
		List<SubnivelDto> subnivelDtos = new ArrayList<>();
		for(AlunoAula aa : aulas){
			SubnivelDto dto = new SubnivelDto();
			dto.setSubnivel(buscarNivelSubnivelDoAluno(idAluno, aa.getIdNivel(), aa.getId().getAula().getId()));
			dto.setAula(aa.getId().getAula().getNome());
			subnivelDtos.add(dto);
		}
		return subnivelDtos;
	}
	
	public String buscarNivelSubnivelDoAluno(Long idAluno, Long idNivel, Long idAula) {

		Integer minutosAula = alunoAulaRepository.getMinutosAulaAluno(idAluno, idAula);
		NivelAlunoEnum nivelAluno = NivelAlunoEnum.getById(idNivel);
		return buscarSubnivel(minutosAula, nivelAluno);
	} 

	public String buscarNivelSubnivelInicialDoAluno(Long idNivel) {
		NivelAlunoEnum nivelAluno = NivelAlunoEnum.getById(idNivel);
		return buscarSubnivel(0, nivelAluno);
	}

	private String buscarSubnivel(Integer minutosAula, NivelAlunoEnum nivelAluno) {
		return switch (nivelAluno) {
			case E1 -> subnivelEssentials1(minutosAula);
			case E2 -> subnivelEssentials2(minutosAula);
			case I1 -> subnivelInter1(minutosAula);
			case I2 -> subnivelInter2(minutosAula);
			case P1 -> subnivelPro1(minutosAula);
			case P2 -> subnivelPro2(minutosAula);
			case M -> subnivelMaster(minutosAula);
		};
	}

	public List<SubnivelDto> progresso(Long idAluno){
		if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
		List<AlunoAula> aulas = alunoAulaRepository.getAulasAluno(idAluno);
		if(aulas.isEmpty()){
			throw new BusinessException("Aluno não possui aulas registradas");
		}
		List<SubnivelDto> subnivelDtos = new ArrayList<>();
		for(AlunoAula aa : aulas){
			SubnivelDto dto = new SubnivelDto();
			dto.setSubnivel(porcentagemConcluida(idAluno, aa.getId().getAula().getId()));
			dto.setAula(aa.getId().getAula().getNome());
			subnivelDtos.add(dto);
		}
		return subnivelDtos;
	}

	public String porcentagemConcluida(Long idAluno, Long idAula) {
		Integer minutosAula = alunoAulaRepository.getMinutosAulaAluno(idAluno, idAula);

		Double porcentagem, minutosConcluidosNoSubnivel, minutosTotaisDoSubnivel;
		if (minutosAula <= MAX_MINUTOS_SUBNIVEL_1) {
			porcentagem = (double) (minutosAula * 100) / (double) MAX_MINUTOS_SUBNIVEL_1;
		} else if (minutosAula <= MAX_MINUTOS_SUBNIVEL_2) {
			minutosConcluidosNoSubnivel = (double) minutosAula - MIN_MINUTOS_SUBNIVEL_2;
			minutosTotaisDoSubnivel = (double) (MAX_MINUTOS_SUBNIVEL_2 - MIN_MINUTOS_SUBNIVEL_2);
			porcentagem = (minutosConcluidosNoSubnivel * 100) / minutosTotaisDoSubnivel;
		} else {
			minutosConcluidosNoSubnivel = (double) minutosAula - MIN_MINUTOS_SUBNIVEL_3;
			minutosTotaisDoSubnivel = (double) (MAX_MINUTOS_SUBNIVEL_3 - MIN_MINUTOS_SUBNIVEL_3);
			porcentagem = (minutosConcluidosNoSubnivel * 100) / minutosTotaisDoSubnivel;
		}

		return String.format("%.2f", porcentagem);
	}

	private String subnivelEssentials1(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.E1A21.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2 && minutosAula <= MAX_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.E1A22.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_3 && minutosAula <= MAX_MINUTOS_SUBNIVEL_3)
			return SubnivelAlunoEnum.E1A23.getDescricao();
		return null;
	}

	private String subnivelEssentials2(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.E2A24.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2 && minutosAula <= MAX_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.E2A25.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_3 && minutosAula <= MAX_MINUTOS_SUBNIVEL_3)
			return SubnivelAlunoEnum.E2A26.getDescricao();
		return null;
	}

	private String subnivelInter1(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.I1B11.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2 && minutosAula <= MAX_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.I1B12.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_3 && minutosAula <= MAX_MINUTOS_SUBNIVEL_3)
			return SubnivelAlunoEnum.I1B13.getDescricao();
		return null;
	}

	private String subnivelInter2(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.I2B14.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2 && minutosAula <= MAX_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.I2B15.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_3 && minutosAula <= MAX_MINUTOS_SUBNIVEL_3)
			return SubnivelAlunoEnum.I2B16.getDescricao();
		return null;
	}

	private String subnivelPro1(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.P1B21.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2 && minutosAula <= MAX_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.P1B22.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_3 && minutosAula <= MAX_MINUTOS_SUBNIVEL_3)
			return SubnivelAlunoEnum.P1B23.getDescricao();
		return null;
	}

	private String subnivelPro2(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.P2B24.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2 && minutosAula <= MAX_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.P2B25.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_3 && minutosAula <= MAX_MINUTOS_SUBNIVEL_3)
			return SubnivelAlunoEnum.P2B26.getDescricao();
		return null;
	}

	private String subnivelMaster(Integer minutosAula) {
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_1 && minutosAula <= MAX_MINUTOS_SUBNIVEL_1)
			return SubnivelAlunoEnum.MC11.getDescricao();
		if (minutosAula >= MIN_MINUTOS_SUBNIVEL_2)
			return SubnivelAlunoEnum.MC12.getDescricao();
		return null;
	}

	private void validarFront(NivelDto dto) throws HttpException {
		if(dto == null || dto.getNome().isBlank() || dto.getNome().isEmpty()) {
			throw new HttpException("Existe(m) campos não preenchido(s)");
		}
	}
}
