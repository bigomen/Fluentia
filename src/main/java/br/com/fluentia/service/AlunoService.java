package br.com.fluentia.service;

import br.com.fluentia.dto.*;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.AlunoAula;
import br.com.fluentia.model.Credito;
import br.com.fluentia.repository.*;
import br.com.fluentia.type.StatusAlunoEnum;
import br.com.fluentia.utils.GeneratePageable;
import br.com.fluentia.utils.GeneratePasswordUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlunoService extends AbstractService {

	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private AlunoAulaRepository alunoAulaRepository;
	@Autowired
	private AulaRepository aulaRepository;
	@Autowired
	private CreditoRepository creditoRepository;
	@Autowired
	private EmailService emailService;
	@Autowired
	private EmpresaRepository empresaRepository;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	@Autowired
	private NivelService nivelService;
	@Autowired
	private PlanoRepository planoRepository;
	@Autowired
	private IuguService iuguService;
	@Autowired
	private AdesaoService adesaoService;
	@Autowired
	private PreMatriculaRepository preMatriculaRepository;

	public void criarAluno(AlunoDto dto){
		if(alunoRepository.validarDadosUnicosCadastro(dto.getEmail(), dto.getNome(), dto.getCpf())) throw new UniqueException("Nome, Email ou CPF");
		if(Objects.nonNull(dto.getIdEmpresa())){
			if(!empresaRepository.existsById(dto.getIdEmpresa())) throw new NotFoundException("Empresa");
		}
		dto.setIdNivelAlunoAulaInicial(dto.getIdNivelAlunoAula());
		Aluno model = dto.dtoToModel();
		String passwordGerado = GeneratePasswordUtils.gerarSenha();
		model.setSenha(GeneratePasswordUtils.crypt(passwordGerado));

		try {
			String idIugu = iuguService.criarClienteIugu(model);
			model.setIdIugu(idIugu);
		}catch (Exception e){
			throw new InternalErroException();
		}
		model = alunoRepository.save(model);

		EmailDto dtoEmail = new EmailDto();
		dtoEmail.setTitulo("Nova senha");
		dtoEmail.setEmail(model.getEmail());
		dtoEmail.setTexto("{\"password\":\"" + passwordGerado + "\"}");

		try {
			emailService.sendMail(dtoEmail);
		}catch (Exception e){
			iuguService.removerClienteIugu(model.getIdIugu());
			alunoRepository.delete(model);
			throw new InternalErroException();
		}
	}

	public void atualizar(AlunoDto dto){
		Aluno aluno = alunoRepository.findById(dto.getId()).orElseThrow(() -> new NotFoundException("Aluno"));
		if(alunoRepository.validarAtualizacao(dto.getCpf(), dto.getEmail(), dto.getNome(), dto.getId())) throw new UniqueException("Nome, Email ou CPF");
		dto.setAtivo(aluno.getAtivo());
		dto.setDataMatricula(aluno.getDataMatricula());
		Aluno model = dto.dtoToModel();
		model.setSenha(aluno.getSenha());
		model.setResetToken(aluno.getResetToken());
		model.setIdNivelAlunoAula(aluno.getIdNivelAlunoAula());
		model.setIdNivelAlunoAulaInicial(aluno.getIdNivelAlunoAulaInicial());
		model.setSaldo(aluno.getSaldo());
		model.setIdIugu(aluno.getIdIugu());
		alunoRepository.save(model);
	}

	@Transactional
	public void desativar(Long id){
		if(alunoRepository.existsById(id)){
			if(alunoRepository.existsByIdAndAtivo(id, StatusAlunoEnum.ATIVO.getStatus())){
				alunoRepository.AlterarStatus(id, StatusAlunoEnum.INATIVO.getStatus());
			}else {
				throw new BusinessException("Aluno já está desativado");
			}

		}else {
			throw new NotFoundException("Aluno");
		}
	}

	@Transactional
	public void ativar(Long id){
		if(alunoRepository.existsById(id)){
			if(alunoRepository.existsByIdAndAtivo(id, StatusAlunoEnum.INATIVO.getStatus())){
				alunoRepository.AlterarStatus(id, StatusAlunoEnum.ATIVO.getStatus());
			}else {
				throw new BusinessException("Aluno já está ativado");
			}

		}else {
			throw new NotFoundException("Aluno");
		}
	}

	public Page<AlunoDto> lista(PaginacaoDto params) {
		Page<Aluno> alunos = alunoRepository.findAll(GeneratePageable.build(params, Sort.by("nome").ascending()));
		return alunos.map(Aluno::modelToDto);
	}

	public AlunoDto porCpf(String cpf){
		Optional<Aluno> aluno = alunoRepository.findByCpf(cpf);
		if(aluno.isEmpty()) throw new NotFoundException("Aluno");
		return aluno.get().modelToDto();
	}

	public Long criarAuto(AlunoDto dto) {
		if(alunoRepository.validarDadosUnicosCadastro(dto.getEmail(), dto.getNome(), dto.getCpf())) throw new UniqueException("Nome, Email ou CPF");
		if(!empresaRepository.existsById(dto.getIdEmpresa())) throw new NotFoundException("Empresa");
		String passwordGerado = GeneratePasswordUtils.gerarSenha();
		Aluno aluno = dto.dtoToModel();
		aluno.setSenha(GeneratePasswordUtils.crypt(passwordGerado));
		aluno.setIdNivelAlunoAula(0L);
		aluno.setIdNivelAlunoAulaInicial(dto.getIdNivelAlunoAula());
		aluno.setAtivo(StatusAlunoEnum.ATIVO.getStatus());
		aluno = alunoRepository.save(aluno);
		try {
			this.enviarEmail(aluno, passwordGerado);
		} catch (IOException | UnirestException e) {
			e.printStackTrace();
		}
		return aluno.getId();
	}

	public void enviarEmail(Aluno aluno, String password) throws IOException, UnirestException {
		var dto = new EmailDto();
		dto.setTitulo("Redefinição de Senha");
		dto.setEmail(aluno.getEmail());
		dto.setTexto("{\"password\":\"" + password + "\"}");

		emailService.sendMail(dto);

	}

	public List<AlunoDto> listarAtivos() {
		List<Aluno> alunosAtivos = alunoRepository.listarPorStatus(StatusAlunoEnum.ATIVO.getStatus());
		return alunosAtivos.stream().map(Aluno::modelToDto).collect(Collectors.toList());
	}

	public AlunoDto porId(Long id){
		Optional<Aluno> aluno = alunoRepository.findById(id);
		if(aluno.isEmpty()) throw new NotFoundException("Aluno");
		return aluno.get().modelToDto();
	}

	@Transactional
	public void addCreditos(CreditoDto dto) {
		if(!alunoRepository.existsById(dto.getIdAluno())) throw new NotFoundException("Aluno");
		Credito credito = dto.dtoToModel();
		credito.setDateTime(LocalDateTime.now());
		creditoRepository.save(credito);
	}

	public Integer saldo(Long idAluno){
		if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
		LocalDateTime dataInicio = LocalDateTime.now().minusMonths(1);
		Integer saldo = creditoRepository.saldoAluno(idAluno, dataInicio);
		if(Objects.isNull(saldo)) return 0;
		return saldo;
	}

	public AlunoDto buscarMe() {
		Aluno aluno = this.getAlunoByTokenLogged();
		AlunoDto dto = aluno.modelToDto();
		dto.setCartao(adesaoService.verificaCartao(aluno.getIdIugu()));
		return dto;
	}

    public List<ReportAlunoDto> reportAluno(Long idAluno){
		if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
		List<AlunoAula> aulas = alunoAulaRepository.getAulasAluno(idAluno);
		if(aulas.isEmpty()){
			throw new BusinessException("Aluno não possui aulas registradas");
		}
		List<ReportAlunoDto> reports = new ArrayList<>();
		for(AlunoAula a : aulas){
			String nivelSubnivelInicialDoAluno = nivelService.buscarNivelSubnivelInicialDoAluno(a.getIdNivel());
			String nivelSubnivelDoAluno = nivelService.buscarNivelSubnivelDoAluno(idAluno, a.getIdNivel(), a.getId().getAula().getId());
			String porcentagemConcluida = nivelService.porcentagemConcluida(idAluno, a.getId().getAula().getId());

			ReportAlunoDto reportAlunoDto = new ReportAlunoDto();
			reportAlunoDto.setNivelSubnivelInicial(nivelSubnivelInicialDoAluno);
			reportAlunoDto.setNivelSubnivelAtual(nivelSubnivelDoAluno);
			reportAlunoDto.setPorcentagemAtual(porcentagemConcluida);
			reportAlunoDto.setAula(a.getId().getAula().getNome());
			reports.add(reportAlunoDto);
		}
		return reports;
	}

	public void criarAlunoAssinatura(AlunoAssinaturaDto dto){
		AlunoDto alunoDto = dto.getAluno();
		criarAluno(alunoDto);
		Optional<Aluno> aluno = alunoRepository.findByEmail(alunoDto.getEmail());
		if(aluno.isEmpty()) throw new BusinessException("Erro ao salvar aluno");
		Map<String,String> body = new HashMap<>();
		body.put("token", dto.getToken());
		adesaoService.adicionarCartao(body, aluno.get());
		body.put("idPlano", String.valueOf(dto.getPlano()));
		adesaoService.criarAssinatura(body, aluno.get());
		preMatriculaRepository.deleteById(dto.getPreMatricula());
	}

	public void atualizarAlunoMigracao(AlunoAssinaturaDto dto){
		Aluno aluno = this.getAlunoByTokenLogged();
		AlunoDto alunoDto = dto.getAluno();
		alunoDto.setId(aluno.getId());
		atualizar(alunoDto);
		Map<String,String> body = new HashMap<>();
		body.put("token", dto.getToken());
		adesaoService.adicionarCartao(body, aluno);
		body.put("idPlano", String.valueOf(dto.getPlano()));
		adesaoService.alterarPlano(body);
	}

	@Transactional
	public List<Long> inserirTodosIugu(){
		List<Aluno> alunosSemIugu = alunoRepository.listaAlunosSemIugu();
		List<Long> ids = new ArrayList<>();
		for(Aluno a : alunosSemIugu){
			try {
				a.setIdIugu(iuguService.criarClienteIugu(a));
				alunoRepository.updateIdIugu(a.getId(), a.getIdIugu());
			}catch (Exception e){
				ids.add(a.getId());
			}

		}
		return ids;
	}
}
