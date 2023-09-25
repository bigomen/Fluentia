package br.com.fluentia.repository;

import br.com.fluentia.model.Credito;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditoRepository extends BaseRepository<Credito> {

    Boolean existsByIdAluno(Long idAluno);

    Optional<Credito> findByIdAlunoPlano(String idAlunoPlano);

    @Query("select c from Credito c where c.idAluno = :idAluno")
    Credito findByIdAluno(Long idAluno);

    @Query("select c from Credito c where c.idAluno = :idAluno and c.idAlunoPlano = :idPlano")
    Credito findByIdAlunoAndIdAlunoPlano(Long idAluno, String idPlano);

    @Query("select max(c.id) from Credito c where c.idAluno = :idAluno")
    Long ultimoCredito(Long idAluno);

    @Query("select sum(c.credito) from Credito c where c.idAluno = :id and c.dateTime > :dataInicio")
    Integer saldoAluno(Long id, LocalDateTime dataInicio);

    @Query("select sum(c.credito) from Credito c where c.idAluno = :id and c.dateTime < :dataInicio")
    Integer saldoExpirado(Long id, LocalDateTime dataInicio);

    @Query(value = "SELECT c.* FROM credito c JOIN aluno_plano a ON a.codigo = c.aluno_plano_codigo JOIN plano p ON p.id = a.plano_id WHERE c.id_aluno = :idAluno " +
            "and DATE(DATE_ADD(c.date_time, INTERVAL p.dias_validade - 10 DAY)) = CURDATE()", nativeQuery = true)
    public Collection<Credito> buscarAlunoComCreditosParaExpirar(@Param("idAluno") Long idAluno);

    @Modifying
    @Query("DELETE FROM Credito c where c.idAlunoPlano = :codigo")
    void apagarPorCodigo(String codigo);

    @Query("select new Credito(c.credito, c.dateTime) from Credito c where c.idAluno = :idAluno order by c.dateTime desc")
    List<Credito> extrato(Long idAluno);

    @Query("select max(c.dateTime) from Credito c where c.idAlunoPlano = :codigo")
    LocalDateTime ultimoCreditoDoPlano(String codigo);
}
