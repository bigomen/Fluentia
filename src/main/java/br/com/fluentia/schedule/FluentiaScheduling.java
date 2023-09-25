package br.com.fluentia.schedule;

import br.com.fluentia.dto.EmailDto;
import br.com.fluentia.dto.PlanoDto;
import br.com.fluentia.model.*;
import br.com.fluentia.repository.AdesaoRepository;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.AulaRecorrenciaRepository;
import br.com.fluentia.repository.AulaRepository;
import br.com.fluentia.service.AdesaoService;
import br.com.fluentia.service.CreditoService;
import br.com.fluentia.service.EmailService;
import br.com.fluentia.service.PlanoService;
import br.com.fluentia.type.StatusAulaEnum;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

@Component
public class FluentiaScheduling {

    private final AlunoRepository alunoRepository;
    private final AdesaoRepository adesaoRepository;
    private final PlanoService planoService;
    private final CreditoService creditoService;
    private final EmailService emailService;
    private final AdesaoService adesaoService;
    private final AulaRecorrenciaRepository aulaRecorrenciaRepository;
    private final AulaRepository aulaRepository;

    @Value("${param.checkout-url}")
    private String checkoutUrl;

    @Autowired
    public FluentiaScheduling(AlunoRepository alunoRepository, AdesaoRepository adesaoRepository, PlanoService planoService,
                              CreditoService creditoService, EmailService emailService, AdesaoService adesaoService, AulaRecorrenciaRepository aulaRecorrenciaRepository, AulaRepository aulaRepository) {
        this.alunoRepository = alunoRepository;
        this.adesaoRepository = adesaoRepository;
        this.planoService = planoService;
        this.creditoService = creditoService;
        this.emailService = emailService;
        this.adesaoService = adesaoService;
        this.aulaRecorrenciaRepository = aulaRecorrenciaRepository;
        this.aulaRepository = aulaRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Scheduled(cron = "0 0 3 ? * *")
    public void renovarCreditos() {
        Collection<Aluno> alunosAtivos = alunoRepository.findAlunosAtivos();

        List<Optional<Adesao>> listAdesaoAtiva = alunosAtivos
                .stream()
                .map(aluno -> adesaoRepository.buscarAdesaoAtivaDoAluno(aluno.getId()))
                .toList();

        alunosAtivos.forEach(aluno -> {
            Optional<Optional<Adesao>> adesaoAluno = listAdesaoAtiva
                    .stream()
                    .filter(Optional::isPresent)
                    .filter(adesao -> adesao.get().getAluno().getId().equals(aluno.getId()))
                    .filter(this::verificaDiaRenovacao)
                    .findFirst();
            if (adesaoAluno.isPresent()) {
                PlanoDto planoDto = planoService.buscarPorId(adesaoAluno.get().get().getPlano().getId());
                creditoService.adicionarCreditos(aluno.getId(), planoDto.getCredito());
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Scheduled(cron = "0 0 4 ? * *")
    public void enviaEmailNovaAdesao() {
        Collection<Adesao> listAdesaoEnvioEmail = adesaoRepository.buscarAdesaoParaEnvioEmail();
        listAdesaoEnvioEmail.forEach(adesao -> {
            if (adesao.getDataAdesao().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                Optional<Aluno> aluno = alunoRepository.findById(adesao.getAluno().getId());
                PlanoDto planoDto = planoService.buscarPorId(adesao.getPlanoNovo().getId());
                try {
                    enviarEmail(aluno.get(), planoDto);
                    adesao.setEnviaEmail(false);
                    adesaoRepository.save(adesao);
                } catch (UnirestException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Scheduled(cron = "0 0 0 ? * *")
    public void verificaAdesaoParaReativar() {
        Collection<Adesao> listAdesaoParaReativar = adesaoRepository.buscarAdesaoParaReativar();
        listAdesaoParaReativar.forEach(adesao -> {
            if (adesao.getDataSuspensao().get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
//                adesaoService.suspenderReativarAdesao(adesao.getCodigo(), PagSeguroAdesaoStatusEnum.ACTIVE);
            }
        });
    }

    private boolean verificaDiaRenovacao(Optional<Adesao> adesao) {
        Calendar dataAdesao = adesao.get().getDataAdesao();
        return dataAdesao.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    private void enviarEmail(Aluno aluno, PlanoDto planoDto) throws UnirestException {
        var email = new EmailDto();
        email.setTitulo("Nova Plano!");
        email.setEmail(aluno.getEmail());
        email.setTexto("{" + "\"alunoNome\":\"" + aluno.getNome() + "\","
                + "\"checkoutUrl\":\""	+ checkoutUrl + "\","
                + "\"codigo\":\""	+ planoDto.getCodigoIugu() + "\""  + "}");

        emailService.sendMailNovoPlano(email);
    }

    @Scheduled(cron = "0 0 01 * * SUN")
    public void recorrencia(){
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
