package br.com.fluentia.service;

import br.com.fluentia.dto.CreditoDto;
import br.com.fluentia.exception.BusinessException;
import br.com.fluentia.model.Credito;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.CreditoRepository;
import br.com.fluentia.security.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CreditoService extends AbstractService {

    @Autowired
    private CreditoRepository creditoRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private UserDAO userRepository;

    public boolean verificaSeCreditoExpira(Long idAluno) {
        Collection<Credito> creditoCollection = creditoRepository.buscarAlunoComCreditosParaExpirar(idAluno);
        return !creditoCollection.isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void adicionarCreditos(Long idAluno, Integer qtdCreditos) {
        Credito credito = creditoRepository.findByIdAluno(idAluno);
        if(Objects.isNull(credito)) throw new BusinessException("Aluno não possui associação com um plano");
        credito.setCredito(credito.getCredito() + qtdCreditos);
        creditoRepository.save(credito);
    }

    public List<CreditoDto> extrato(Long idAluno){
        List<Credito> creditos = creditoRepository.extrato(idAluno);
        if(!creditos.isEmpty()){
            return creditos.stream().map(Credito::modelToDto).collect(Collectors.toList());
        }
        return null;
    }
}
