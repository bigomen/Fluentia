package br.com.fluentia.repository;

import br.com.fluentia.model.AulaRecorrencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AulaRecorrenciaRepository extends BaseRepository<AulaRecorrencia>{

    List<AulaRecorrencia> findAllByOrderByIdDesc();

    @Query("select new AulaRecorrencia(ar.id, ar.credito, ar.duracao, ar.idModalidade," +
            " ar.idProfessor, ar.idTipoAula, ar.nome, ar.cron) from AulaRecorrencia ar where ar.ativo = :status")
    List<AulaRecorrencia> listaCompleta(Integer status);

    AulaRecorrencia findByCreditoAndDuracaoAndIdModalidadeAndIdProfessorAndIdTipoAulaAndNome(
            Integer credito,
            Integer duracao,
            Long idModalidade,
            Long idProfessor,
            Long idTipoAula,
            String nome
    );

    @Query("select n.id from AulaRecorrencia ar join ar.nivelList n where ar.id = :id")
    List<Long> getNiveis(Long id);
}
