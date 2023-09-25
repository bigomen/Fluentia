package br.com.fluentia.service;

import br.com.fluentia.dto.*;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.*;
import br.com.fluentia.repository.*;
import br.com.fluentia.type.StatusAulaAlunoEnum;
import br.com.fluentia.type.StatusAulaEnum;
import br.com.fluentia.utils.CalendarUtils;
import br.com.fluentia.utils.DateUtils;
import br.com.fluentia.utils.GeneratePageable;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AulaService extends AbstractService {

	private static final int MIN_MINUTOS_SUBNIVEL_1 = 0;
	private static final int MAX_MINUTOS_SUBNIVEL_1 = 1259;
	private static final int MIN_MINUTOS_SUBNIVEL_2 = 1260;
	private static final int MAX_MINUTOS_SUBNIVEL_2 = 2459;
	private static final int MIN_MINUTOS_SUBNIVEL_3 = 2460;
	private static final int MAX_MINUTOS_SUBNIVEL_3 = 3600;

	@Autowired
	private AulaRepository aulaRepository;
	@Autowired
	private TipoAulaRepository tipoAulaRepository;
	@Autowired
	private NivelRepository nivelRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private TemaRepository temaRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private ZoomService zoomService;
	@Autowired
	private ProfessorService professorService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private PresencaRepository presencaRepository;
	@Autowired
	private NivelService nivelService;
	@Autowired
	private ModalidadeRepository modalidadeRepository;
	@Autowired
	private CreditoRepository creditoRepository;
	@Autowired
	private AlunoAulaRepository alunoAulaRepository;
	@Autowired
	private AulaRecorrenciaRepository aulaRecorrenciaRepository;

	public Long criar(AulaDto dto){
		if(!modalidadeRepository.existsById(dto.getIdModalidade())) throw new NotFoundException("Modalidade");
		if(!professorRepository.existsById(dto.getIdProfessor())) throw new NotFoundException("Professor");
		if(Objects.nonNull(dto.getIdTema())){
			if(!temaRepository.existsById(dto.getIdTema())) throw new NotFoundException("Tema");
		}
		if(!tipoAulaRepository.existsById(dto.getIdTipoAula())) throw new NotFoundException("Tipo aula");

		Aula aula = aulaRepository.save(dto.dtoToModel());
		return aula.getId();
	}

	public void atualizar(AulaDto dto){
		if(!aulaRepository.existsById(dto.getId())) throw new NotFoundException("Aula");
		if(!modalidadeRepository.existsById(dto.getIdModalidade())) throw new NotFoundException("Modalidade");
		if(!professorRepository.existsById(dto.getIdProfessor())) throw new NotFoundException("Professor");
		if(Objects.nonNull(dto.getIdTema())){
			if(!temaRepository.existsById(dto.getIdTema())) throw new NotFoundException("Tema");
		}
		if(!tipoAulaRepository.existsById(dto.getIdTipoAula())) throw new NotFoundException("Tipo aula");
		dto.setAtivo(aulaRepository.statusAula(dto.getId()));
		aulaRepository.save(dto.dtoToModel());
	}

	public Page<AulaDto> lista(PaginacaoDto params) {
		Page<Aula> aulas = aulaRepository.findAll(GeneratePageable.build(params, Sort.by("dataAula").descending()));
		Page<AulaDto> aulasDto = aulas.map(Aula::modelToDto);
		for(AulaDto a : aulasDto.getContent()){
			a.setAlunos(alunoAulaRepository.getQuantAlunos(a.getId()));
		}
		return aulasDto;
	}

	@Transactional
	public void desativar(Long id){
		if(aulaRepository.existsById(id)){
			aulaRepository.alterarStatus(id, StatusAulaEnum.INATIVO.getStatus());
		}else {
			throw new NotFoundException("Aula");
		}
	}

	@Transactional
	public void ativar(Long id){
		if(aulaRepository.existsById(id)){
			aulaRepository.alterarStatus(id, StatusAulaEnum.ATIVO.getStatus());
		}else {
			throw new NotFoundException("Aula");
		}
	}

	public List<AulaDto> buscarAtivos() {
		List<Aula> aulasAtivas = aulaRepository.listarPorStatus(StatusAulaEnum.ATIVO.getStatus());
		return aulasAtivas.stream().map(Aula::modelToDto).collect(Collectors.toList());
	}

	public List<AulaDto> buscarPorData(LocalDate dataAula) {
		LocalDateTime inicioDia = dataAula.atTime(0,0,0);
		LocalDateTime fimDia = dataAula.atTime(23,59,59);
		Professor professor = this.verificarProfessor();
		if(professor != null){
			List<Aula> aulas = aulaRepository.aulasPorDataProfessor(StatusAulaEnum.ATIVO.getStatus(), inicioDia, fimDia, professor.getId());
			return aulas.stream().map(Aula::modelToDto).collect(Collectors.toList());
		}
		List<Aula> aulas = aulaRepository.aulasPorData(StatusAulaEnum.ATIVO.getStatus(), inicioDia, fimDia);
		Aluno aluno = this.verificarAluno();
		if(aluno != null){
			List<Long> aulasAluno = alunoAulaRepository.getIdAulas(aluno.getId());
			List<AulaDto> dtos = new ArrayList<>();
			for(Aula aula : aulas){
				AulaDto dto = aula.modelToDto();
				if(aulasAluno.isEmpty()) dto.setStatus(StatusAulaAlunoEnum.DISPONIVEL.getDescricao());
				for(Long id : aulasAluno){
					if(Objects.equals(dto.getId(), id)){
						dto.setStatus(StatusAulaAlunoEnum.AGENDADA.getDescricao());
						break;
					}else {
						dto.setStatus(StatusAulaAlunoEnum.DISPONIVEL.getDescricao());
					}
				}
				dtos.add(dto);
			}
			return dtos;
		}
		return aulas.stream().map(Aula::modelToDto).collect(Collectors.toList());
	}

	private StatusAulaAlunoEnum validateStatus(Set<Aluno> alunos) {
		var aluno = super.getAlunoByTokenLogged();
		if (!alunos.contains(aluno)) {
			return StatusAulaAlunoEnum.DISPONIVEL;
		} else {
			return StatusAulaAlunoEnum.AGENDADA;
		}
	}

	public AulaDto buscarPorId(Long id) {
		Optional<Aula> aula = aulaRepository.findById(id);
		if(aula.isEmpty()) throw new NotFoundException("Aula");
		return aula.get().modelToDto();
	}

	public void agendar(Map<String, Long> body){
		Optional<Aluno> aluno;
		if (Objects.nonNull(body.get("idAluno"))) {
			aluno = alunoRepository.findById(body.get("idAluno"));
		}else {
			aluno = alunoRepository.findById(getAlunoByTokenLogged().getId());
		}
		Optional<Aula> aula = aulaRepository.findById(body.get("idAula"));
		if(aluno.isEmpty()) throw new NotFoundException("Aluno");
		if(aula.isEmpty()) throw new NotFoundException("Aula");
		if(Objects.equals(aula.get().getIdTipoAula(), TipoAula.PRIVATE_CLASS)){
			List<Aluno> alunos = alunoAulaRepository.getAlunosAulas(aula.get().getId());
			if(!alunos.isEmpty()) throw new BusinessException("Está aula privada já possui aluno agendado");
		}
		if(aula.get().getDataAula().isBefore(LocalDateTime.now())){
			throw new BusinessException("Não é possivel agendar aulas anteriores ao momento atual");
		}
		Integer saldo = validateAulaAluno(aula.get(), aluno.get());
		Long ultimoCredito = creditoRepository.ultimoCredito(aluno.get().getId());
		Optional<Credito> credito = creditoRepository.findById(ultimoCredito);
		if(credito.isEmpty()) throw new NotFoundException("Credito");
		credito.get().setCredito(credito.get().getCredito() - aula.get().getCredito());
		AlunoAula alunoAula = new AlunoAula();
		AlunoAulaId id = new AlunoAulaId();
		id.setAula(aula.get());
		id.setAluno(aluno.get());
		alunoAula.setId(id);
		alunoAulaRepository.save(alunoAula);
		creditoRepository.save(credito.get());
		try {
			enviarEmailAgendamento(aula.get(), aluno.get());
		}catch (UnirestException e){
			throw new InternalErroException();
		}
	}

	private void enviarEmailAgendamento(Aula aula, Aluno aluno) throws UnirestException {
		var dto = new EmailDto();

		var tipo = tipoAulaRepository.getById(aula.getIdTipoAula());
		var file = CalendarUtils.generateCalendar(aula, tipo.getNome(), UUID.randomUUID().toString());
		dto.setTitulo("You booked a class!");
		dto.setEmail(aluno.getEmail());
		dto.setTexto("{" + "\"alunoNome\":\"" + aluno.getNome() + "\","
				+ "\"aulaData\":\""	+ DateUtils.formatInvite(aula.getDataAula() + ":00Z").toUpperCase() + "\","
				+ "\"tipo\":\""	+ tipo.getNome() + "\","
				+ "\"aulaZoom\":\""	+ aula.getJoinUrl() + "\"" + "}");
		emailService.sendMailAgendamento(dto,file);
	}

	private void validarCancelamentoAula(Aula aula){
		TipoAula tipoAula = tipoAulaRepository.getById(aula.getIdTipoAula());

		long dataAulaMilis = DateUtils.formatInMilis(aula.getDataAula() + ":00");
		long dataAtual = new Date().getTime();

		long dataAulaMenosUmaHora = dataAulaMilis - (1000 * 60 * 60);
		long dataAulaMenos24Horas = dataAulaMilis - (1000 * 60 * 60 * 24);

		if ((tipoAula.getQtde() == 1) && (dataAtual > dataAulaMenos24Horas)) {
			throw new BusinessException("Aula Privativa só poderá ser cancelada até 24 horas do início.");
		}
		else if ((dataAtual > dataAulaMenosUmaHora)) {
			throw new BusinessException("Aula só poderá ser cancelada até 1 hora do início.");
		}
	}

	public List<AulaDto> buscarAtivosModal(LocalDate dataAula) {
		LocalDateTime inicioDia = dataAula.atTime(0,0,0);
		LocalDateTime fimDia = dataAula.atTime(23,59,59);
		List<Aula> aulas = aulaRepository.aulasPorData(StatusAulaEnum.ATIVO.getStatus(),inicioDia, fimDia);
		return aulas.stream().map(Aula::modelToDto).collect(Collectors.toList());
	}

	@Transactional
	public void cancelar(Map<String, Long> body){
		Optional<Aluno> aluno;
		if (Objects.nonNull(body.get("idAluno"))) {
			aluno = alunoRepository.findById(body.get("idAluno"));
		}else {
			aluno = alunoRepository.findById(getAlunoByTokenLogged().getId());
		}
		Optional<Aula> aula = aulaRepository.findById(body.get("idAula"));
		if(aluno.isEmpty()) throw new NotFoundException("Aluno");
		if(aula.isEmpty()) throw new NotFoundException("Aula");
		Optional<AlunoAula> alunoAula = alunoAulaRepository.findByIdAulaAndIdAluno(aula.get().getId(), aluno.get().getId());
		if(alunoAula.isEmpty()) throw new BusinessException("Aluno não cadastrado nessa aula");
		validarCancelamentoAula(aula.get());
		Long ultimoCredito = creditoRepository.ultimoCredito(aluno.get().getId());
		Optional<Credito> credito = creditoRepository.findById(ultimoCredito);
		if(credito.isEmpty()) throw new NotFoundException("Credito");
		credito.get().setCredito(credito.get().getCredito() + aula.get().getCredito());
		alunoAulaRepository.delete(alunoAula.get());
		creditoRepository.save(credito.get());
		try {
			enviarEmailCancelamento(aula.get(), aluno.get());
		} catch (UnirestException e) {
			throw new InternalErroException();
		}
	}



	@Transactional
	public AulaDto criarZoom(Map<String, Long> body){
		var professor = professorRepository.getById(body.get("idProfessor"));
		var aula = aulaRepository.getById(body.get("idAula"));
		if (professor == null) {
			throw new NotFoundException("Professor");
		}
		if (professor.getIdZoom() == null || professor.getIdZoom().isEmpty()) {
			var idZoom = professorService.buscarIdZoom(professor.getId());
			professor.setIdZoom(idZoom);
			professorRepository.save(professor);
		}

		var tipo = tipoAulaRepository.getById(aula.getIdTipoAula());
		var dataAula = aula.getDataAula() + ":00";
		var zoomDto = new ZoomDto();
		var duracao = aula.getDuracao() * 2;
		zoomDto.setDuration(String.valueOf(duracao));
		zoomDto.setScheduleFor(professor.getIdZoom());
		zoomDto.setStartTime(dataAula);
		zoomDto.setTopic(tipo.getNome());

		Map<String, Object> mapZoom = zoomService.createMeeting(zoomDto);
		if (mapZoom != null) {
			var startUrl = (String) mapZoom.get("start_url");
			var joinUrl = (String) mapZoom.get("join_url");
			aula.setJoinUrl(joinUrl);
			aula.setStartUrl(startUrl);
			aulaRepository.save(aula);
			return aula.modelToDto();
		}
		throw new InternalErroException();
	}

	public List<AulaDto> buscarProAluno(Long idAluno, LocalDate dataAula) {
		List<Aula> aulas;
		if(Objects.nonNull(idAluno) && Objects.nonNull(dataAula)){
			LocalDateTime inicioDia = dataAula.atTime(0,0,0);
			LocalDateTime fimDia = dataAula.atTime(23,59,59);
			if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
			aulas = aulaRepository.aulasPorAlunoComData(idAluno, StatusAulaEnum.ATIVO.getStatus(), inicioDia, fimDia);
		}else if (Objects.nonNull(idAluno)){
			if(!alunoRepository.existsById(idAluno)) throw new NotFoundException("Aluno");
			aulas = aulaRepository.aulasPorAluno(idAluno, StatusAulaEnum.ATIVO.getStatus());
		}else if (Objects.nonNull(dataAula)){
			LocalDateTime inicioDia = dataAula.atTime(0,0,0);
			LocalDateTime fimDia = dataAula.atTime(23,59,59);
			Aluno aluno = this.getAlunoByTokenLogged();
			aulas = aulaRepository.aulasPorAlunoComData(aluno.getId(), StatusAulaEnum.ATIVO.getStatus(), inicioDia, fimDia);
		}
		else {
			Aluno aluno = this.getAlunoByTokenLogged();
			aulas = aulaRepository.aulasPorAluno(aluno.getId(), StatusAulaEnum.ATIVO.getStatus());
		}
		return aulas.stream().map(Aula::modelToDto).collect(Collectors.toList());
	}

	public List<AulaDto> buscarPorProfessor(String dataAula) {
		Professor professor = this.getProfessor();
		List<Aula> aulas;
		if(Objects.nonNull(dataAula)){
			Integer ano = Integer.valueOf(StringUtils.substringBefore(dataAula, "-"));
			Integer mes = Integer.valueOf(StringUtils.substringAfter(dataAula, "-"));
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, mes - 1);
			calendar.set(Calendar.YEAR, ano);
			Integer ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			LocalDateTime dataInicio = LocalDateTime.of(ano, mes, 01, 00, 00);
			LocalDateTime dataFim = LocalDateTime.of(ano, mes, ultimoDia, 23, 59, 59);
			aulas = aulaRepository.getAulasPorProfessorData(professor.getId(), StatusAulaEnum.ATIVO.getStatus(), dataInicio, dataFim);
		}else {
			aulas = aulaRepository.findByIdProfessorAndAtivoOrderByDataAulaDesc(professor.getId(), StatusAulaEnum.ATIVO.getStatus());
		}
		return aulas.stream().map(Aula::modelToDto).collect(Collectors.toList());
	}

	private Integer validateAulaAluno(Aula aula, Aluno aluno){
		Integer saldo = creditoRepository.saldoAluno(aluno.getId(), LocalDateTime.now().minusMonths(1));
		Optional<AlunoAula> alunoAula = alunoAulaRepository.findByIdAulaAndIdAluno(aula.getId(), aluno.getId());
		if(alunoAula.isPresent()) throw new BusinessException("Aluno já agendado para está aula");
		if (saldo == null || aula.getCredito() > saldo) {
			throw new BusinessException("Você não tem créditos suficientes para esta aula. Compre de forma avulsa ou faça um upgrade de plano.");
		}
		long dataAula = DateUtils.formatInMilis(aula.getDataAula()+":00");
		if (dataAula < new Date().getTime()) {
			throw new BusinessException("Aula já iniciada! Não é possível realizar o agendamento.");
		}
		long dataAulaMenosUmaHora = dataAula - (1000 * 60 * 60);
		if (new Date().getTime() > dataAulaMenosUmaHora) {
			throw new BusinessException("Aula só poderá ser agendada até 1 hora do início.");
		}
		return saldo;
	}

	private void enviarEmailCancelamento(Aula aula, Aluno aluno) throws UnirestException {
		var dto = new EmailDto();
		var tipo = tipoAulaRepository.getById(aula.getIdTipoAula());
		dto.setTitulo("You canceled a class!");
		dto.setEmail(aluno.getEmail());
		dto.setTexto("{" + "\"alunoNome\":\"" + aluno.getNome() + "\","
				+ "\"aulaData\":\""	+ DateUtils.formatInvite(aula.getDataAula() + ":00Z").toUpperCase() + "\","
				+ "\"tipo\":\""	+ tipo.getNome() + "\""  + "}");
		emailService.sendMailCancelamento(dto);

	}

	public List<AlunoAulaDto> buscarAlunos(Long idAula) {
		if(!aulaRepository.existsById(idAula)) throw new NotFoundException("Aula");
		List<AlunoAula> alunos = alunoAulaRepository.alunosPorAulaComPresenca(idAula);
		return alunos.stream().map(AlunoAula::modelToDto).collect(Collectors.toList());
 	}

	private boolean validarPresenca(AlunoDto alunoDto, Long id) {
		var presencaFilter = new Presenca();
		presencaFilter.setIdAluno(alunoDto.getId());
		presencaFilter.setIdAula(id);
		var listPresenca = presencaRepository.findAll(Example.of(presencaFilter));
		if(listPresenca.size()>0)
			return true;
		else
			return false;

	}

	public List<AulaDto> detalhe(AulaFiltroDto filtros){
		Aluno aluno;
		if(Objects.isNull(filtros.getIdAluno())){
			aluno = this.getAlunoByTokenLogged();
		}else {
			Optional<Aluno> alunoOpt = alunoRepository.findById(filtros.getIdAluno());
			if(alunoOpt.isEmpty()) throw new NotFoundException("Aluno");
			aluno = alunoOpt.get();
		}
		List<Aula> aulas = alunoAulaRepository.getAulasAluno(aluno, filtros);
		List<Long> aulasAluno = alunoAulaRepository.getIdAulas(aluno.getId());
		List<AulaDto> dtos = new ArrayList<>();
		for(Aula aula : aulas){
			AulaDto dto = aula.modelToDto();
			if(aulasAluno.isEmpty()) dto.setStatus(StatusAulaAlunoEnum.DISPONIVEL.getDescricao());
			for(Long id : aulasAluno){
				if(Objects.equals(dto.getId(), id)){
					dto.setStatus(StatusAulaAlunoEnum.AGENDADA.getDescricao());
					break;
				}else {
					dto.setStatus(StatusAulaAlunoEnum.DISPONIVEL.getDescricao());
				}
			}
			dtos.add(dto);
		}
		return dtos;
	}

	public Set<LocalDate> buscarAulaDia() {
		List<LocalDateTime> aulasFuturas = aulaRepository.aulasFuturas();
		Set<LocalDate> datas = new HashSet<>();
		for(LocalDateTime aula : aulasFuturas){
			 datas.add(aula.toLocalDate());
		}
		return datas;
	}

	public Map<String, String> generateReport(LocalDate dataAula) {
		LocalDateTime inicioDia = dataAula.atTime(0,0,0);
		LocalDateTime fimDia = dataAula.atTime(23,59,59);
		List<Aula> aulas = aulaRepository.aulasRelatorio(StatusAulaEnum.ATIVO.getStatus(), inicioDia, fimDia);
		var html = "<html><body><table border='1'><tr>" +
				"<th>ID</th>" +
				"<th>Data Aula</th>" +
				"<th>Zoom Aluno</th>" +
				"<th>Materiais</th>" +
				"<th>Nome Aluno</th>" +
				"<th>Email Aluno</th>" +
				"<th>Professor</th>" +
				"<th>Status</th></tr>" +
				"{{ conteudo }}" +
				"</table></body></html>";

		StringBuilder sb = new StringBuilder("");
		for (Aula aula : aulas) {
			List<Aluno> alunos = alunoAulaRepository.alunosPorAulaRelatorio(aula.getId());
			List<String> materiais = aulaRepository.getMaterialPorAula(aula.getId());
			StringBuilder material = new StringBuilder(" ");
			if(!materiais.isEmpty()){
				for(String m : materiais){
					material.append(" ").append(m).append(" -");
				}
				material = new StringBuilder(StringUtils.chop(material.toString()));
			}
			for (Aluno aluno : alunos) {
				sb.append("<tr>")
						.append("<td>").append(aula.getId()).append("</td>")
						.append("<td>").append(aula.getDataAula()).append("</td>")
						.append("<td>").append(aula.getJoinUrl()).append("</td>")
						.append("<td>").append(material).append("</td>")
						.append("<td>").append(aluno.getNome()).append("</td>")
						.append("<td>").append(aluno.getEmail()).append("</td>")
						.append("<td>").append(aula.getProfessor().getNome()).append("</td>")
						.append("<td>").append(popularPresenca(aula,aluno)).append("</td>")
						.append("</tr>");
			}

		}
		Map<String, String> retorno = new HashMap<>();
		retorno.put("retorno", html.replace("{{ conteudo }}",sb.toString()));
		
		return retorno;
	}

	private String popularPresenca(Aula aula, Aluno aluno) {
		var presenca = new Presenca();
		presenca.setIdAula(aula.getId());
		presenca.setIdAluno(aluno.getId());
		var qttPresenca = presencaRepository.count(Example.of(presenca));
		if(qttPresenca==0) {
			return "<b style='color:red'>NO SHOW</b>";
		}
		return "<b style='color:green'>SHOW</b>";
	}

	@Transactional
	public void confirmarPresenca(AlunoPresencaDto dto) {
		Long idAula = dto.getIdAula();
		if(!aulaRepository.existsById(idAula)) throw new NotFoundException("Aula");
		for(AlunoAulaDto aa : dto.getAlunos()){
			if(!alunoRepository.existsById(aa.getIdAluno())) throw new NotFoundException("Aluno");
			alunoAulaRepository.confirmarPresenca(aa.getIdAluno(), idAula, aa.getPresente());
			Aluno aluno = alunoAulaRepository.dadosParaEmailPresenca(aa.getIdAluno());
			List<String> materiais = aulaRepository.getMaterialPorAula(idAula);
			StringBuilder material = new StringBuilder(" ");
			if(!materiais.isEmpty()){
				for(String m : materiais){
					material.append(" ").append(m).append(" -");
				}
				material = new StringBuilder(StringUtils.chop(material.toString()));
			}
			enviaEmailConfirmacaoPresenca(aluno, material.toString());
		}
	}

	@Transactional
	public void alterarProfessor(Long idAula, Long idProfessor) {
		if(!aulaRepository.existsById(idAula)) throw new NotFoundException("Aula");
		if(!professorRepository.existsById(idProfessor)) throw new NotFoundException("Professor");
		aulaRepository.trocarProfessor(idAula, idProfessor);
	}

//	private static boolean concluiSubnivel1(Aluno aluno, int minutosAulaTotal) {
//		return (aluno.getMinutosAula() >= MIN_MINUTOS_SUBNIVEL_1 && aluno.getMinutosAula() <= MAX_MINUTOS_SUBNIVEL_1) && minutosAulaTotal > MAX_MINUTOS_SUBNIVEL_1;
//	}
//
//	private static boolean concluiSubnivel2(Aluno aluno, int minutosAulaTotal) {
//		return (aluno.getMinutosAula() >= MIN_MINUTOS_SUBNIVEL_2 && aluno.getMinutosAula() <= MAX_MINUTOS_SUBNIVEL_2) && minutosAulaTotal > MAX_MINUTOS_SUBNIVEL_2
//				&& (!aluno.getIdNivelAlunoAula().equals(NivelAlunoEnum.M.getId()));
//	}
//
//	private static boolean concluiSubnivel3(Aluno aluno, int minutosAulaTotal) {
//		return (aluno.getMinutosAula() >= MIN_MINUTOS_SUBNIVEL_3 && aluno.getMinutosAula() <= MAX_MINUTOS_SUBNIVEL_3) && minutosAulaTotal > MAX_MINUTOS_SUBNIVEL_3;
//	}

	private void enviaEmailNovoNivel(Aluno aluno, String nivelSubnivelDoAluno) {
		var emailDto = new EmailDto();
		emailDto.setTitulo("Congratulations!");
		emailDto.setEmail(aluno.getEmail());
		emailDto.setTexto("{" + "\"alunoNome\":\"" + aluno.getNome() + "\","
				+ "\"nivel\":\""	+ nivelSubnivelDoAluno + "\"" + "}");
		try {
			emailService.sendMailNovoNivel(emailDto);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	private void enviaEmailConfirmacaoPresenca(Aluno aluno, String material) {
		var emailDto = new EmailDto();
		emailDto.setTitulo("Class is done!");
		emailDto.setEmail(aluno.getEmail());
		emailDto.setTexto("{" + "\"alunoNome\":\"" + aluno.getNome() + "\","
				+ "\"material\":\""	+ material + "\"" + "}");
		try {
			emailService.sendMailPosAula(emailDto);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	public void recorrencia(Map<String, String> body){
		Long idAula = Long.valueOf(body.get("idAula"));
		String cron = body.get("cron");
		Optional<Aula> aula = aulaRepository.findById(idAula);
		if(aula.isEmpty()) throw new NotFoundException("Aula");
		AulaRecorrencia aulaValid = aulaRecorrenciaRepository.findByCreditoAndDuracaoAndIdModalidadeAndIdProfessorAndIdTipoAulaAndNome(
				aula.get().getCredito(),
				aula.get().getDuracao(),
				aula.get().getIdModalidade(),
				aula.get().getIdProfessor(),
				aula.get().getIdTipoAula(),
				aula.get().getNome()
		);
		if(Objects.nonNull(aulaValid)) throw new BusinessException("Aula já possui recorrência");
		AulaRecorrencia aulaRecorrencia = new AulaRecorrencia();
		aulaRecorrencia.setAtivo(1);
		aulaRecorrencia.setCredito(aula.get().getCredito());
		aulaRecorrencia.setDuracao(aula.get().getDuracao());
		aulaRecorrencia.setIdModalidade(aula.get().getIdModalidade());
		aulaRecorrencia.setIdProfessor(aula.get().getIdProfessor());
		aulaRecorrencia.setIdTipoAula(aula.get().getIdTipoAula());
		aulaRecorrencia.setNome(aula.get().getNome());
		String horario = String.valueOf(aula.get().getDataAula().toLocalTime());
		String data = String.valueOf(aula.get().getDataAula().toLocalDate());
		cron = cron.concat("|").concat(data).concat("|").concat(horario);
		aulaRecorrencia.setCron(cron);
		if(aula.get().getNivelList() != null){
			List<Nivel> nivelList = new ArrayList<>();
			for(Nivel n : aula.get().getNivelList()){
				Nivel nivel = new Nivel();
				nivel.setId(n.getId());
				nivelList.add(nivel);
			}
			aulaRecorrencia.setNivelList(nivelList);
		}
		aulaRecorrenciaRepository.save(aulaRecorrencia);
	}

	public List<AulaRecorrenciaDto> listaRecorrencia(){
		List<AulaRecorrencia> aulas = aulaRecorrenciaRepository.findAllByOrderByIdDesc();
		return aulas.stream().map(AulaRecorrencia::modelToDto).collect(Collectors.toList());
	}

	public void apagarRecorrencia(Long id){
		if(!aulaRecorrenciaRepository.existsById(id)) throw new NotFoundException("Recorrência");
		aulaRecorrenciaRepository.deleteById(id);
	}

	public List<AulaDto> buscarPorDataMensal(String dataAula){
		String ano = StringUtils.substringBefore(dataAula, "-");
		String mes = StringUtils.substringAfter(dataAula, "-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, Integer.parseInt(mes)-1);
		calendar.set(Calendar.YEAR, Integer.parseInt(ano));
		Integer ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		LocalDateTime dataInicio = LocalDateTime.of(Integer.parseInt(ano), Integer.parseInt(mes),  01, 00, 00, 00);
		LocalDateTime dataFim = LocalDateTime.of(Integer.parseInt(ano), Integer.parseInt(mes), ultimoDia, 23, 59, 59);
		List<Aula> aulas = aulaRepository.aulasPorData(StatusAulaEnum.ATIVO.getStatus(), dataInicio, dataFim);
		return aulas.stream().map(Aula::modelToDto).collect(Collectors.toList());
	}

	public void testeRecorrencia(){
		List<AulaRecorrencia> recorrencias = aulaRecorrenciaRepository.listaCompleta(StatusAulaEnum.ATIVO.getStatus());
		if(!recorrencias.isEmpty()){
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, 2);
			LocalDate maxLocalDate = LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault()).toLocalDate();
			LocalDateTime maxDate = LocalDateTime.of(maxLocalDate.getYear(), maxLocalDate.getMonth(), maxLocalDate.getDayOfMonth(), 23,59,59);
			for(AulaRecorrencia ar : recorrencias){
				LocalDateTime dataUltimaAula = aulaRepository.getDataUltimaAula(ar.getCredito(), ar.getDuracao(), ar.getIdModalidade(), ar.getIdProfessor(), ar.getIdTipoAula(), ar.getNome());
				String cronWithDate = StringUtils.substringBeforeLast(ar.getCron(), "|");
				String periodo = StringUtils.substringBefore(cronWithDate, "|");
				if(dataUltimaAula == null){
					LocalTime horario = LocalTime.parse(StringUtils.substringAfterLast(ar.getCron(), "|"));
					LocalDate dataAula = LocalDate.parse(StringUtils.substringAfter(cronWithDate, "|"));
					dataUltimaAula = LocalDateTime.of(dataAula, horario);
				}
				List<Long> idsNivel = aulaRecorrenciaRepository.getNiveis(ar.getId());
				if(!idsNivel.isEmpty()){
					List<Nivel> niveis = new ArrayList<>();
					for(Long idNivel : idsNivel){
						Nivel nivel = new Nivel();
						nivel.setId(idNivel);
						niveis.add(nivel);
					}
					ar.setNivelList(niveis);
				}
				if(Objects.equals(periodo, "d")){
					while (maxDate.isAfter(dataUltimaAula)){
						Aula aula = ar.recorrenciaToAula();
						dataUltimaAula = dataUltimaAula.plusDays(1);
						aula.setDataAula(dataUltimaAula);
						aulaRepository.save(aula);
					}
				}else if(Objects.equals(periodo, "s")){
					while (maxDate.isAfter(dataUltimaAula)){
						Aula aula = ar.recorrenciaToAula();
						dataUltimaAula = dataUltimaAula.plusWeeks(1);
						aula.setDataAula(dataUltimaAula);
						aulaRepository.save(aula);
					}
				}else if(Objects.equals(periodo, "m")){
					while (maxDate.isAfter(dataUltimaAula)){
						Aula aula = ar.recorrenciaToAula();
						dataUltimaAula = dataUltimaAula.plusMonths(1);
						aula.setDataAula(dataUltimaAula);
						aulaRepository.save(aula);
					}
				}
			}
		}
	}
}
