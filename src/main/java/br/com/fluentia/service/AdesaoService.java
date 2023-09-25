package br.com.fluentia.service;

import br.com.fluentia.dto.AdesaoCanceladaDto;
import br.com.fluentia.dto.AlunoPlanoDto;
import br.com.fluentia.dto.FaturaDto;
import br.com.fluentia.dto.InadimplentesDto;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.model.*;
import br.com.fluentia.repository.*;
import br.com.fluentia.type.StatusPlanoEnum;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.threeten.extra.Days;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

@Service
public class AdesaoService extends AbstractService {

    @Value("${iugu.valor.credito}")
    private BigDecimal valorCredito;

    @Autowired
    private IuguService iuguService;
    @Autowired
    private CreditoRepository creditoRepository;
    @Autowired
    private AdesaoRepository adesaoRepository;
    @Autowired
    private PlanoRepository planoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CreditoService creditoService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AlunoPlanoRepository alunoPlanoRepository;

    public AlunoPlanoDto buscarPlanoPorAlunoLogado() {
        Aluno aluno = super.getAlunoByTokenLogged();
        AlunoPlano alunoPlano = alunoPlanoRepository.findByIdAluno(aluno.getId());
        if(Objects.isNull(alunoPlano)) throw new NotFoundException("Aluno não possui plano");
        return alunoPlano.modelToDto();
    }

    public void cancelarAdesao(Map<String, String> body) {
        Aluno aluno = this.getAlunoByTokenLogged();
        AlunoPlano alunoPlano = alunoPlanoRepository.findByIdAluno(aluno.getId());
        if(Objects.isNull(alunoPlano)) throw new BusinessException("Aluno não associado a este plano");
        alunoPlano.setDataTrocaAdesao(null);
        alunoPlano.setDataReativacao(null);
        alunoPlano.setDataSuspensao(null);
        alunoPlano.setDataCancelamento(LocalDateTime.now());
        alunoPlano.setMotivoCancelamento(body.get("motivo"));
        iuguService.cancelarAssinatura(alunoPlano.getCodigo());
        alunoPlanoRepository.save(alunoPlano);
    }

    public void suspenderReativarAdesao(Long idAluno) {
        Aluno aluno;
        if(idAluno == null){
            aluno = this.getAlunoByTokenLogged();
        }else {
            Optional<Aluno> optAluno = alunoRepository.findById(idAluno);
            if(optAluno.isEmpty()) throw new NotFoundException("Aluno");
            aluno = optAluno.get();
        }
        AlunoPlano alunoPlano = alunoPlanoRepository.findByIdAluno(aluno.getId());
        if(Objects.isNull(alunoPlano)) throw new NotFoundException("Plano");
        if(alunoPlano.getDataCancelamento() != null) throw new BusinessException("Plano cancelado não pode ser reativado");
        if(alunoPlano.getDataSuspensao() != null){
            alunoPlano.setDataSuspensao(null);
            alunoPlano.setDataCancelamento(null);
            alunoPlano.setDataReativacao(LocalDateTime.now());
            alunoPlano.setDataTrocaAdesao(null);
            iuguService.reativarAssinatura(alunoPlano.getCodigo());
        }else {
            alunoPlano.setDataSuspensao(LocalDateTime.now());
            alunoPlano.setDataCancelamento(null);
            alunoPlano.setDataReativacao(null);
            alunoPlano.setDataTrocaAdesao(null);
            iuguService.suspenderAssinatura(alunoPlano.getCodigo());
        }
        alunoPlanoRepository.save(alunoPlano);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Collection<AdesaoCanceladaDto> buscarAdesaoCancelada() {
        return adesaoRepository.listaAdesaoCancelada();
    }

    private void verificaSePossuiTempoMinimoParaSuspensao(Adesao adesao) {
        Aluno aluno = alunoRepository.findById(adesao.getAluno().getId())
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        LocalDate dataMatricula = aluno.getDataMatricula();
        int mesesMatricula = Math.abs(Period.between(LocalDate.parse(dataMatricula.toString()), LocalDate.now()).getMonths());
        int anosMatricula = Math.abs(Period.between(LocalDate.parse(dataMatricula.toString()), LocalDate.now()).getYears());
        if (mesesMatricula < 2 && anosMatricula == 0) {
            throw new RuntimeException("Você ainda não possui o mínimo de 2 meses de matrícula para pausar seu plano!");
        }

        if (adesao.getDataSuspensao() != null) {
            LocalDateTime dataSuspensao = LocalDateTime.ofInstant(adesao.getDataSuspensao().toInstant(), adesao.getDataSuspensao().getTimeZone().toZoneId());
            int years = Math.abs(Period.between(dataSuspensao.toLocalDate(), LocalDate.now()).getYears());
            if (years < 1) {
                throw new RuntimeException("Você ainda não completou o mínimo de 1 ano após a sua última suspensão!");
            }
        }
    }

    public void adicionarCartao(Map<String, String> body, Aluno aluno){
        if(aluno == null){
            aluno = this.getAlunoByTokenLogged();
        }
        String token = body.get("token");
        Map<String, String> paymentBody = new HashMap<>();
        paymentBody.put("customer_id", String.valueOf(aluno.getIdIugu()));
        paymentBody.put("description", "cartao do " + aluno.getNome());
        paymentBody.put("token", token);
        paymentBody.put("set_as_default", String.valueOf(true));
        iuguService.inserirFormaPagamento(paymentBody);
        alunoRepository.save(aluno);
    }

    public void gerarCobranca(Map<String,String> body){
        Aluno aluno = this.getAlunoByTokenLogged();
        Integer quantidade = Integer.parseInt(body.get("quantidade"));
        Map requestBody = new HashMap();
        Map item = new HashMap();

        List<Map> items = new ArrayList<>();
        item.put("description", "Adicionar "+ quantidade +" creditos");
        item.put("quantity", 5);
        BigDecimal valorTotal = valorCredito.multiply(BigDecimal.valueOf(quantidade));
        item.put("price_cents", valorTotal.movePointRight(2).intValueExact());
        items.add(item);

        requestBody.put("customer_id", aluno.getIdIugu());
        requestBody.put("items", items);

        try {
            requestBody.put("customer_payment_method_id", iuguService.getCartao(aluno.getIdIugu()));
        }catch (Exception e){
            throw new BusinessException("Erro no cartão registrado");
        }

        try {
            iuguService.cobrancaDireta(requestBody);
        }catch (Exception e){
            throw new BusinessException("Erro ao gerar cobrança");
        }
        Credito credito = new Credito();
        credito.setCredito(quantidade);
        credito.setDateTime(LocalDateTime.now());
        credito.setIdAluno(aluno.getId());
        creditoRepository.save(credito);

    }

    private AlunoPlano gerarModelAlunoPlano(Long idAluno, Long idPlano){
        AlunoPlano model = new AlunoPlano();
        model.setDataAdesao(LocalDateTime.now());
        model.setIdAluno(idAluno);
        model.setIdPlano(idPlano);
        model.setDataTrocaAdesao(null);
        model.setDataCancelamento(null);
        model.setDataReativacao(null);
        model.setDataSuspensao(null);
        model.setMotivoCancelamento(null);
        return model;
    }

    private Credito generateModelCredito(Long idAluno, String codigo, Integer creditos){
        Credito credito = new Credito();
        credito.setDateTime(LocalDateTime.now());
        credito.setIdAluno(idAluno);
        credito.setIdAlunoPlano(codigo);
        credito.setCredito(creditos);
        return credito;
    }

    @Transactional
    public void criarAssinatura(Map<String, String> body, Aluno aluno){
        if(aluno == null){
            aluno = this.getAlunoByTokenLogged();
        }
        Long idPlano = Long.valueOf(body.get("idPlano"));
        Optional<Plano> plano = planoRepository.findById(idPlano);
        if(plano.isEmpty()) throw new NotFoundException("Plano");
        Plano planoAnterior = alunoPlanoRepository.getPlanoAluno(aluno.getId());
        if(Objects.equals(plano.get().getAtivo(), StatusPlanoEnum.INATIVO.getStatus())) throw new BusinessException("Plano está desativado");
        if(plano.get().getPreco().intValueExact() == 0 && plano.get().getCodigoIugu() == null){
            AlunoPlano alunoPlano = gerarModelAlunoPlano(aluno.getId(), idPlano);
            alunoPlano.setCodigo(RandomStringUtils.randomAlphabetic(32).toUpperCase());
            alunoPlanoRepository.save(alunoPlano);
            creditoRepository.save(generateModelCredito(aluno.getId(), alunoPlano.getCodigo(), plano.get().getCredito()));
            return;
        }
        Map planoIugu = iuguService.buscarPlanoIugu(plano.get().getCodigoIugu());
        Map assinatura = new HashMap();
        assinatura.put("plan_identifier", planoIugu.get("identifier"));
        assinatura.put("customer_id", aluno.getIdIugu());
        Integer adicao = 0;
        if(planoAnterior != null){
            Optional<AlunoPlano> alunoPlano = alunoPlanoRepository.findByIdAlunoAndIdPlano(aluno.getId(), planoAnterior.getId());
            if(alunoPlano.isPresent()){
                Credito credito = creditoRepository.findByIdAlunoAndIdAlunoPlano(aluno.getId(), alunoPlano.get().getCodigo());
                adicao = credito.getCredito();
                creditoRepository.apagarPorCodigo(alunoPlano.get().getCodigo());
                alunoPlanoRepository.apagarPorCodigo(alunoPlano.get().getCodigo());
            }
        }
        AlunoPlano model = gerarModelAlunoPlano(aluno.getId(), idPlano);
        String codigoAssinatura = iuguService.criarAssinaturaIugu(assinatura);
        model.setCodigo(codigoAssinatura);
        alunoPlanoRepository.save(model);
        Credito novoCredito = generateModelCredito(aluno.getId(), codigoAssinatura, plano.get().getCredito() + adicao);
        creditoRepository.save(novoCredito);
    }

    public void alterarPlano(Map<String, String> body){
        Aluno aluno = this.getAlunoByTokenLogged();
        Long idPlano = Long.valueOf(body.get("idPlano"));
        Plano planoAnterior = alunoPlanoRepository.getPlanoAluno(aluno.getId());
        if(Objects.isNull(planoAnterior)) throw new NotFoundException("Plano do aluno");
        Optional<Plano> planoNovo = planoRepository.findById(idPlano);
        if(planoNovo.isEmpty()) throw new NotFoundException("Novo plano");
        if(Objects.equals(planoNovo.get().getAtivo(), StatusPlanoEnum.INATIVO.getStatus())) throw new BusinessException("Plano está desativado");
        Optional<AlunoPlano> alunoPlano = alunoPlanoRepository.findByIdAlunoAndIdPlano(aluno.getId(), planoAnterior.getId());
        if(alunoPlano.isEmpty()) throw new BusinessException("Aluno não possui plano");
        if(alunoPlano.get().getDataCancelamento() != null || alunoPlano.get().getDataSuspensao() != null) throw new BusinessException("Não é possivel a troca pois o plano está suspenso ou cancelado");
        Map iuguPlano = iuguService.buscarPlanoIugu(planoNovo.get().getCodigoIugu());
        iuguService.alterarPlanoAssinatura(alunoPlano.get().getCodigo(), (String) iuguPlano.get("identifier"));
        alunoPlano.get().setDataTrocaAdesao(LocalDate.now());
        alunoPlano.get().setIdPlano(planoNovo.get().getId());
        alunoPlanoRepository.save(alunoPlano.get());
    }

    public List<InadimplentesDto> alunosInadimplentes(){
        List<String> emails = iuguService.getAlunosSuspensos();
        if(!emails.isEmpty()){
            List<InadimplentesDto> dtos = new ArrayList<>();
            for(String email : emails){
                AlunoPlano alunoPlano = alunoPlanoRepository.findByEmailAluno(email);
                LocalDateTime dataUltimoCredito = creditoRepository.ultimoCreditoDoPlano(alunoPlano.getCodigo());
                LocalDate dataVencimento = dataUltimoCredito.plusDays(alunoPlano.getPlano().getDiasValidade()).toLocalDate();
                InadimplentesDto dto = new InadimplentesDto();
                dto.setNome(alunoPlano.getAluno().getNome());
                dto.setPlano(alunoPlano.getPlano().getNome());
                dto.setEmpresa(alunoPlano.getAluno().getEmpresa().getNome());
                dto.setDataVencimento(dataVencimento);
                dto.setDiasVencidos(0);
                if(dataVencimento.isAfter(LocalDate.now())){
                    Integer diasVencidos = Days.between(LocalDate.now(), dataVencimento).getAmount();
                    dto.setDiasVencidos(diasVencidos);
                }
                dtos.add(dto);
            }
            return dtos;
        }
        return null;
    }

    public List<FaturaDto> fatura(Long idAluno){
        Aluno aluno;
        if(Objects.nonNull(idAluno)){
            Optional<Aluno> optAluno = alunoRepository.findById(idAluno);
            if(optAluno.isPresent()){
                aluno = optAluno.get();
            }else throw new NotFoundException("Aluno");
        }else aluno = this.getAlunoByTokenLogged();
        List<FaturaDto> faturas = iuguService.buscarFatura(aluno.getIdIugu());
        if(!faturas.isEmpty()){
            return faturas;
        }
        return null;
    }

    public Boolean verificaCartao(String codigo){
        return iuguService.verificarCartao(codigo);
    }
}
