package br.com.fluentia.service;

import br.com.fluentia.dto.AulaDto;
import br.com.fluentia.dto.CardDto;
import br.com.fluentia.model.*;
import br.com.fluentia.repository.*;
import br.com.fluentia.type.StatusAulaEnum;
import br.com.fluentia.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DashboardService extends AbstractService {

	@Autowired
	AulaRepository aulaRepository;
	@Autowired 
	TipoAulaRepository tipoAulaRepository;
	@Autowired
	ProfessorRepository professorRepository;
	@Autowired
	private CreditoRepository creditoRepository;
	@Autowired
	private AlunoAulaRepository alunoAulaRepository;
	
	public List<CardDto> dashboard(String dataAula) {
		Professor professor = this.getProfessor();

		List<CardDto> cardList = new ArrayList<>();

		Integer ano = Integer.valueOf(StringUtils.substringBefore(dataAula, "-"));
		Integer mes = Integer.valueOf(StringUtils.substringAfter(dataAula, "-"));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, mes - 1);
		calendar.set(Calendar.YEAR, ano);
		Integer ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		LocalDateTime dataInicio = LocalDateTime.of(ano, mes, 01, 00, 00);
		LocalDateTime dataFim = LocalDateTime.of(ano, mes , ultimoDia, 23, 59);

		List<TipoAula> tiposAula = aulaRepository.getTiposPorProfessor(professor.getId(), dataInicio, dataFim);

		for(TipoAula ta : tiposAula){
			Integer count = aulaRepository.countAulaPorProfessorTipoAula(ta.getId(), professor.getId());
			CardDto card = new CardDto();
			card.setKey(ta.getId().toString());
			card.setLabel(ta.getNome());
			card.setValue(String.valueOf(count));
			card.setBackground(popularBackground(ta.getId()));
			cardList.add(card);
		}

		return cardList;
	}

	private String popularBackground(Long id) {
		if(id.longValue()==1L || id.longValue()==6) {
			return "green";
		}else if(id.longValue()==2L || id.longValue()==7) {
			return "blue";
		}else if(id.longValue()==3L || id.longValue()==8) {
			return "yellow";
		}else if(id.longValue()==4L || id.longValue()==9) {
			return "gray";
		}else if(id.longValue()==5L) {
			return "red";
		}
		return "black";
	}
	
	public Map<String,Object> cardAlunos(){
		var aluno = super.getAlunoByTokenLogged();
		var map = new HashMap<String,Object>();
		var listAulas = new ArrayList<AulaDto>();
		var isFreq = Boolean.FALSE;
		map.put("cards", populateCreditos(aluno));
		map.put("aulas", populateAulas(aluno, listAulas,map));
		map.put("listAulas", listAulas);
		return map;
	}

	private List<CardDto> populateAulas(Aluno aluno, List<AulaDto> listAulas,Map map) {

		LocalDate dataAula = LocalDate.now();
		Integer totalAulas = 0;
		Integer totalAulasAgendadas= 0;
		LocalDateTime inicioDia = dataAula.atTime(0,0,0);
		LocalDateTime fimDia = dataAula.atTime(23,59,59);
		List<Aula> aulas = aulaRepository.aulasPorData(StatusAulaEnum.ATIVO.getStatus(), inicioDia, fimDia);
		var isFreq = Boolean.FALSE;
		for (Aula aula : aulas) {
			totalAulas++;
			List<Aluno> alunos = alunoAulaRepository.alunosPorAula(aula.getId());
			if(Objects.nonNull(alunos)) {
				totalAulasAgendadas++;
				var dto = aula.modelToDto();
				dto.setProfessor(professorRepository.getById(dto.getIdProfessor()).modelToDto());
				dto.setTipoAula(tipoAulaRepository.getById(dto.getIdTipoAula()).modelToDto());
				dto.setJoinUrl(null);
				dto.setStartUrl(null);
				dto.setLinkGoogleClass(null);
				listAulas.add(dto);

				var ts = DateUtils.parse(aula.getDataAula()+":00.000");
				long time = new Date().getTime() -  ts.getTime();
				time=time/(1000*60*60*24);

				if(time<=7) {
					isFreq = Boolean.TRUE;
				}

			}
			map.put("isFreq", isFreq);
		}

		var list = new ArrayList<CardDto>();
		var cardDto = new CardDto();
		cardDto.setKey("1");
		cardDto.setLabel("Aulas agendadas");
		cardDto.setValue(totalAulasAgendadas.toString());
		cardDto.setBackground("blue");
		list.add(cardDto);

		cardDto = new CardDto();
		cardDto.setKey("2");
		cardDto.setLabel("Faltas");
		cardDto.setValue("0");
		cardDto.setBackground("gray");
		list.add(cardDto);

		cardDto = new CardDto();
		cardDto.setKey("3");
		cardDto.setLabel("Total de aulas do mês");
		cardDto.setValue(totalAulas.toString());
		cardDto.setBackground("black");
		list.add(cardDto);
		
		return list;
	}

	private List<CardDto> populateCreditos(Aluno aluno) {
		var list = new ArrayList<CardDto>();
		var cardDto = new CardDto();
		LocalDateTime dataInicio = LocalDateTime.now().minusMonths(1);
		Integer saldo = creditoRepository.saldoAluno(aluno.getId(), dataInicio);
		cardDto.setKey("1");
		cardDto.setLabel("Créditos ativos");
		if(Objects.nonNull(saldo))
			cardDto.setValue(saldo.toString());
		else
			cardDto.setValue("0");
		cardDto.setBackground("green");
		list.add(cardDto);
		
		cardDto = new CardDto();
		cardDto.setKey("2");
		cardDto.setLabel("Créditos expirados");
		Integer expirados =  creditoRepository.saldoExpirado(aluno.getId(), dataInicio);
		cardDto.setValue("0");
		if(Objects.nonNull(expirados)){
			cardDto.setValue(expirados.toString());
		}
		cardDto.setBackground("red");
		list.add(cardDto);
		
		cardDto = new CardDto();
		cardDto.setKey("3");
		cardDto.setLabel("Créditos a vencer (10d)");
		cardDto.setValue("0");
		LocalDateTime dataVencer = LocalDateTime.now().minusDays(10);
		Integer saldoaVencer = creditoRepository.saldoAluno(aluno.getId(), dataVencer);
		if(Objects.nonNull(saldoaVencer)){
			cardDto.setValue(saldoaVencer.toString());
		}
		cardDto.setBackground("yellow");
		list.add(cardDto);
		
		return list;
		
	}

	public String nivel() {
		var aluno = getAlunoByTokenLogged();
		if(aluno.getIdNivelAlunoAula()==0)
			return "true";
		return "false";
	}

}
