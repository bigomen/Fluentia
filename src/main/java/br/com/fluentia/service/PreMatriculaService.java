package br.com.fluentia.service;

import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.dto.PreMatriculaDto;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.exception.NotFoundException;
import br.com.fluentia.exception.UniqueException;
import br.com.fluentia.model.Plano;
import br.com.fluentia.model.PreMatricula;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.EmpresaRepository;
import br.com.fluentia.repository.PlanoRepository;
import br.com.fluentia.repository.PreMatriculaRepository;
import br.com.fluentia.security.dao.UserDAO;
import br.com.fluentia.security.model.User;
import br.com.fluentia.type.StatusPlanoEnum;
import br.com.fluentia.utils.GeneratePageable;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PreMatriculaService {

    @Autowired
    private PreMatriculaRepository preMatriculaRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AlunoService alunoService;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private PlanoRepository planoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private UserDAO userRepository;

    @Value("${proposta.link}")
    private String link;

    @Transactional(rollbackFor = Exception.class)
    public void criar(PreMatriculaDto dto) {
        if(!empresaRepository.existsById(dto.getIdEmpresa())) throw new NotFoundException("Empresa");
        if(Objects.nonNull(dto.getId())){
            if(!preMatriculaRepository.existsById(dto.getId())) throw new NotFoundException("Pré-Matricula");
            if(preMatriculaRepository.validarNome(dto.getNome(), dto.getId())) throw new UniqueException("Nome");
            if(preMatriculaRepository.validarEmail(dto.getEmail(), dto.getId())) throw new UniqueException("Email");
        }else {
            if(preMatriculaRepository.existsByNome(dto.getNome())) throw new UniqueException("Nome");
            if(preMatriculaRepository.existsByEmail(dto.getEmail())) throw new UniqueException("Email");
        }
        if(alunoRepository.existsByEmail(dto.getEmail())) throw new BusinessException("Email utilizado por um aluno cadastrado");
        if(alunoRepository.existsByNome(dto.getNome())) throw new BusinessException("Nome utilizado por um aluno cadastrado");
        PreMatricula pre = preMatriculaRepository.save(dto.dtoToModel());
        Map<String, String> body = new HashMap<>();
        body.put("id", pre.getId().toString());
        gerarEmailProposta(body);
        gerarEmailAdmin(pre);
    }

    public PreMatriculaDto findById(Long id) {
        PreMatricula preMatricula = preMatriculaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pré-Matrícula"));
        return preMatricula.modelToDto();
    }

    public Page<PreMatriculaDto> lista(PaginacaoDto params) {
        Page<PreMatricula> preMatriculas = preMatriculaRepository.findAll(GeneratePageable.build(params, Sort.by("nome").ascending()));
        return preMatriculas.map(PreMatricula::modelToDto);
    }

    public void gerarEmailProposta(Map<String, String> body) {
        Long idPreMatricula = Long.valueOf(body.get("id"));
        Optional<PreMatricula> preMatricula = preMatriculaRepository.findById(idPreMatricula);
        if(preMatricula.isEmpty()) throw new NotFoundException("Pré-matricula");
        body.put("titulo","Proposta!");
        body.put("email", preMatricula.get().getEmail());
        if(body.get("plano") != null){
            Long idPlano = Long.valueOf(body.get("plano"));
            Optional<Plano> plano = planoRepository.findById(idPlano);
            if(plano.isEmpty()) throw new NotFoundException("Plano");
            if(Objects.equals(plano.get().getAtivo(), StatusPlanoEnum.INATIVO.getStatus())) throw new BusinessException("Plano está desativado");
            body.put("texto","{" + "\"alunoNome\":\"" + preMatricula.get().getNome() + "\","
                    + "\"link\":\"" + this.link + "pm=" + preMatricula.get().getId() + "&p=" + plano.get().getId() + "\"" + "}");
        }else {
            body.put("texto","{" + "\"alunoNome\":\"" + preMatricula.get().getNome() + "\","
                    + "\"link\":\"" + this.link + "pm=" + preMatricula.get().getId() + "\"" + "}");
        }
        try {
            emailService.sendMailProposta(body);
        } catch (UnirestException e) {
            throw new InternalErroException();
        }
    }

    public void gerarEmailAdmin(PreMatricula preMatricula){
        List<User> admins = (List<User>) userRepository.findAll();
        if(!admins.isEmpty()){
            Map<String, String> body = new HashMap<>();
            body.put("titulo","Proposta!");
            for(User a : admins){
                body.put("email", a.getEmail());
                body.put("texto","{" + "\"alunoNome\":\"" + preMatricula.getNome() + "\","
                        + "\"link\":\"" + this.link + "pm=" + preMatricula.getId() + "\"" + "}");
                try {
                    emailService.sendMailProposta(body);
                } catch (UnirestException e) {
                    throw new InternalErroException();
                }
            }
        }
    }
}