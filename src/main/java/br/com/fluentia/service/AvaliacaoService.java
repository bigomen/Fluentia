package br.com.fluentia.service;

import br.com.fluentia.dto.AvaliacaoDto;
import br.com.fluentia.dto.EmailDto;
import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.Avaliacao;
import br.com.fluentia.model.Professor;
import br.com.fluentia.repository.*;
import br.com.fluentia.utils.GeneratePageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvaliacaoService extends AbstractService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private AulaRepository aulaRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private NivelRepository nivelRepository;

	public void avaliacaoGeral(AvaliacaoDto dto) {
		Avaliacao avaliacao = avaliacaoRepository.save(dto.dtoToModel());
		
		var aluno = alunoRepository.getById(avaliacao.getIdAluno());

		try {
			var emailDto = new EmailDto();
			emailDto.setEmail(aluno.getEmail());
			if(avaliacao.getIdAula()==null) {
				emailDto.setTitulo("Student Monthly Progress Report - " + avaliacao.getMesAvaliacao());
			}else {
				emailDto.setTitulo("Student Report - Class:" + avaliacao.getIdAula());
			}
			
			emailDto.setTexto(gerarJsonAvaliacao(avaliacao));
			
			if(avaliacao.getIdAula()==null) {
				emailService.sendAvaliacao(emailDto);
			}
			
		} catch (Exception e) {
			throw new InternalErroException();
		}
	}

	private String gerarJsonAvaliacao(Avaliacao avaliacao) {
		var sb = new StringBuffer("{");
		var professor = professorRepository.getById(avaliacao.getIdProfessor());
		var aluno = alunoRepository.getById(avaliacao.getIdAluno());
		var nivel = nivelRepository.getById(aluno.getIdNivelAlunoAula());
		
		sb.append("\"mesAvaliacao\":\"").append(avaliacao.getMesAvaliacao()).append("\",");
		sb.append("\"aluno\":\"").append(aluno.getNome()).append("\",");
		sb.append("\"nivel\":\"").append(nivel.getNome()).append("\",");
		sb.append("\"professor\":\"").append(professor.getNome()).append("\",");
		
		if(avaliacao.getPointGrammar()==null) {
			sb.append("\"grammar\":\"").append("not rated").append("\",");
		}else {
			sb.append("\"grammar\":\"").append(avaliacao.getPointGrammar()).append("\",");
		}
		if(avaliacao.getPointComprehension()==null) {
			sb.append("\"comprehension\":\"").append("not rated").append("\",");
		}else {
			sb.append("\"comprehension\":\"").append(avaliacao.getPointComprehension()).append("\",");
		}
		if(avaliacao.getPointPronunciation()==null) {
			sb.append("\"pronunciation\":\"").append("not rated").append("\",");
		}else {
			sb.append("\"pronunciation\":\"").append(avaliacao.getPointPronunciation()).append("\",");
		}
		if(avaliacao.getPointDiscourse()==null) {
			sb.append("\"discourse\":\"").append("not rated").append("\",");
		}else {
			sb.append("\"discourse\":\"").append(avaliacao.getPointDiscourse()).append("\",");
		}
		if(avaliacao.getPointLexicalResource()==null) {
			sb.append("\"lexical\":\"").append("not rated").append("\",");
		}else {
			sb.append("\"lexical\":\"").append(avaliacao.getPointLexicalResource()).append("\",");
		}
		
		sb.append("\"good\":\"").append(avaliacao.getWriteGood()).append("\",");
		sb.append("\"improvement\":\"").append(avaliacao.getNeedsImprovement()).append("\",");
		sb.append("\"suggestion\":\"").append(avaliacao.getSugestion()).append("\"");
		sb.append("}");
		
		return sb.toString();
	}

	public Page<AvaliacaoDto> lista(PaginacaoDto params) {
		Professor professor = this.verificarProfessor();
		Page<Avaliacao> avaliacoes;
		if(professor != null){
			Avaliacao avaliacao = new Avaliacao();
			avaliacao.setIdProfessor(professor.getId());
			Example<Avaliacao> example = Example.of(avaliacao);
			avaliacoes = avaliacaoRepository.findAll(example, GeneratePageable.build(params, Sort.by("mesAvaliacao").descending()));
		}else {
			avaliacoes = avaliacaoRepository.findAll(GeneratePageable.build(params, Sort.by("mesAvaliacao").descending()));
		}
		return avaliacoes.map(Avaliacao::modelToDto);
	}

	public Page<AvaliacaoDto> listaMensal(Integer pagina) {
		Pageable pageable = PageRequest.of(pagina, 20, Sort.by("mesAvaliacao").descending());
		Page<Avaliacao> avaliacoes = avaliacaoRepository.findAllByMesAvaliacaoIsNotNull(pageable);
		return avaliacoes.map(Avaliacao::modelToDto);
	}
	
	public List<AvaliacaoDto> listaMensalAluno(Long idAluno) {
		Aluno aluno;
		if(Objects.nonNull(idAluno)){
			Optional<Aluno> a = alunoRepository.findById(idAluno);
			if(a.isEmpty()) throw new NotFoundException("Aluno");
			aluno = a.get();
		}else {
			aluno = this.getAlunoByTokenLogged();
		}
		List<Avaliacao> avaliacoes = avaliacaoRepository.avaliacoesMensalAluno(aluno.getId());
		return avaliacoes.stream().map(Avaliacao::modelToDto).collect(Collectors.toList());
	}

	public List<AvaliacaoDto> porAluno(Long idAluno) {
		if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
		List<Avaliacao> avaliacoes = avaliacaoRepository.findAllByIdAlunoOrderByMesAvaliacaoDesc(idAluno);
		return avaliacoes.stream().map(Avaliacao::modelToDto).collect(Collectors.toList());
	}
}
