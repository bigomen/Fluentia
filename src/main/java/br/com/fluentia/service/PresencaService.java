package br.com.fluentia.service;

import br.com.fluentia.dto.RelatorioFiltroDto;
import br.com.fluentia.dto.RelatorioPresencaComFrequenciaDto;
import br.com.fluentia.dto.RelatorioPresencaDto;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.AlunoAula;
import br.com.fluentia.repository.AlunoAulaRepository;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.PresencaRepository;
import br.com.fluentia.type.StatusAulaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class PresencaService extends AbstractService {

	@Autowired
	private PresencaRepository presencaRepository;
	@Autowired
	private AlunoRepository alunoRepository;
	@Autowired
	private AlunoAulaRepository alunoAulaRepository;

	public RelatorioPresencaComFrequenciaDto relatorio(RelatorioFiltroDto params) {
		if(!alunoRepository.existsById(params.getIdAluno())) throw new NotFoundException("Aluno");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, params.getMes().intValue() - 1);
		calendar.set(Calendar.YEAR, params.getAno().intValue());
		Integer ultimoDia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		LocalDateTime dataInicio = LocalDateTime.of(params.getAno().intValue(), params.getMes().intValue(), 01, 00,00,00);
		LocalDateTime dataFim = LocalDateTime.of(params.getAno().intValue(), params.getMes().intValue(), ultimoDia, 23,59,59);
		List<AlunoAula> aulasAgendadas = alunoAulaRepository.getAulasAlunoComData(StatusAulaEnum.ATIVO.getStatus(), params.getIdAluno(), dataInicio, dataFim);
		if(!aulasAgendadas.isEmpty()){
			RelatorioPresencaComFrequenciaDto dto = new RelatorioPresencaComFrequenciaDto();
			List<RelatorioPresencaDto> relatoriosDtos = new ArrayList<>();
			Integer aulasTotais = aulasAgendadas.size();
			List<AlunoAula> alunoAulaComPresenca = aulasAgendadas.stream().filter(AlunoAula::getPresente).toList();
			Integer aulasPresente = alunoAulaComPresenca.size();
			Double presenca = ((aulasPresente.doubleValue() * 100) / aulasTotais.doubleValue());
			DecimalFormat df = new DecimalFormat("00.00");
			dto.setTaxaFrequencia(df.format(presenca).concat("%"));
			for(AlunoAula aa : aulasAgendadas){
				RelatorioPresencaDto relatorio = new RelatorioPresencaDto(
						aa.getId().getAula().getId(),
						aa.getId().getAula().getDataAula().toString(),
						aa.getId().getAula().getProfessor().getNome(),
						aa.getPresente(),
						aa.getId().getAula().getNome()
				);
				relatoriosDtos.add(relatorio);
			}
			dto.setRelatorioPresencaDto(relatoriosDtos);
			return dto;
		}
		return null;

	}

	private static boolean possuiMesAno(RelatorioFiltroDto params, RelatorioPresencaDto presenca) {
        if (params.getAno() == null && params.getMes() == null)
            return true;
        
        
		int mes = Integer.parseInt(presenca.getDataAula().substring(5,7));
		int ano = Integer.parseInt(presenca.getDataAula().substring(0,4));
        
        if (params.getMes() == null && params.getAno() == ano)
            return true;
        
		return params.getMes() == mes && params.getAno() == ano;
	}
}
