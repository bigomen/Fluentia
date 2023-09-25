package br.com.fluentia.repository.custom;

import br.com.fluentia.model.Adesao;
import br.com.fluentia.model.Aluno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class AdesaoRepositoryCustomImpl implements AdesaoRepositoryCustom{

    private EntityManager entityManager;

    @Autowired
    public AdesaoRepositoryCustomImpl(EntityManager entityManager)
    {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Adesao> buscarAdesaoAtivaDoAluno(Long idAluno) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Adesao> criteriaQuery = builder.createQuery(Adesao.class);
        Root<Adesao> root = criteriaQuery.from(Adesao.class);

        Predicate equalAluno = builder.equal(root.get("aluno"), idAluno);
        Predicate dataCancelamentoNull = builder.isNull(root.get("dataCancelamento"));
        Predicate dataSuspensaoNull = builder.isNull(root.get("dataSuspensao"));
        Predicate dataReativacaoNotNull = builder.isNotNull(root.get("dataReativacao"));
        Predicate reativacaoMaiorSuspensao = builder.greaterThan(root.get("dataReativacao"), root.get("dataSuspensao"));

        Predicate and = builder.and(dataReativacaoNotNull, reativacaoMaiorSuspensao);
        Predicate or = builder.or(dataSuspensaoNull, and);
        Predicate where = builder.and(dataCancelamentoNull, or, equalAluno);

        criteriaQuery.where(where);
        TypedQuery<Adesao> typedQuery = entityManager.createQuery(criteriaQuery);

        try {
            return Optional.of(typedQuery.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Adesao> buscarAdesaoParaEnvioEmail() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Adesao> criteriaQuery = builder.createQuery(Adesao.class);
        Root<Adesao> root = criteriaQuery.from(Adesao.class);
        Join<Adesao, Aluno> joinAluno = root.join("aluno", JoinType.INNER);
        Predicate alunoAtivo = builder.equal(joinAluno.get("ativo"), 1);

        Predicate equalEnviaEmail = builder.equal(root.get("enviaEmail"), true);
        Predicate dataTrocaNotNull = builder.isNotNull(root.get("dataTroca"));
        Predicate where = builder.and(equalEnviaEmail, dataTrocaNotNull, alunoAtivo);

        criteriaQuery.where(where);
        TypedQuery<Adesao> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }

    @Override
    public Collection<Adesao> buscarAdesaoParaReativar() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Adesao> criteriaQuery = builder.createQuery(Adesao.class);
        Root<Adesao> root = criteriaQuery.from(Adesao.class);

        Join<Adesao, Aluno> joinAluno = root.join("aluno", JoinType.INNER);
        Predicate alunoAtivo = builder.equal(joinAluno.get("ativo"), 1);

        Predicate dataCancelamentoNull = builder.isNull(root.get("dataCancelamento"));
        Predicate dataSuspensaoNotNull = builder.isNotNull(root.get("dataSuspensao"));
        Predicate dataReativacaoNull = builder.isNull(root.get("dataReativacao"));

        Predicate and = builder.and(dataCancelamentoNull, dataSuspensaoNotNull, dataReativacaoNull);
        Predicate where = builder.and(and, alunoAtivo);

        criteriaQuery.where(where);
        TypedQuery<Adesao> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultList();
    }
}