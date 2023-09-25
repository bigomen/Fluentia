package br.com.fluentia.repository;

import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.AlunoAula;
import br.com.fluentia.repository.custom.AlunoRepositoryCustom;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoAulaRepository extends BaseRepository<AlunoAula>, AlunoRepositoryCustom {

    @Query("select aa from AlunoAula aa join aa.id.aluno al join aa.id.aula au where al.id = :idAluno and au.id = :idAula")
    Optional<AlunoAula> findByIdAulaAndIdAluno(Long idAula, Long idAluno);

    @Query("select a from AlunoAula aa join aa.id.aluno a join aa.id.aula au where au.id = :idAula order by a.nome")
    List<Aluno> alunosPorAula(Long idAula);

    @Query("select aa from AlunoAula aa join aa.id.aluno a join aa.id.aula au where au.id = :idAula order by a.nome")
    List<AlunoAula> alunosPorAulaComPresenca(Long idAula);

    @Query("select new Aluno(a.id, a.email, a.nome) from AlunoAula aa join aa.id.aluno a join aa.id.aula au where au.id = :idAula")
    List<Aluno> alunosPorAulaRelatorio(Long idAula);

    @Modifying
    @Query("update AlunoAula aa set aa.presente = :presente where aa.id.aluno.id = :idAluno and aa.id.aula.id = :idAula")
    void confirmarPresenca(Long idAluno, Long idAula, Boolean presente);

    @Query("select new Aluno(a.email, a.nome) from Aluno a where a.id = :id")
    Aluno dadosParaEmailPresenca(Long id);

    @Query("select sum(a.duracao) from AlunoAula aa join aa.id.aula a join aa.id.aluno al where al.id = :idAluno and a.id = :idAula and aa.presente = true")
    Integer getMinutosAulaAluno(Long idAluno, Long idAula);

    @Query("select count(al.id) from AlunoAula aa join aa.id.aula a join aa.id.aluno al where a.id = :id")
    Integer getQuantAlunos(Long id);

    @Query("select new AlunoAula(a.id, a.nome, aa.idNivel) from AlunoAula aa join aa.id.aula a join aa.id.aluno al where al.id = :idAluno group by a.id, a.nome")
    List<AlunoAula> getAulasAluno(Long idAluno);

    @Query("select a.id from AlunoAula aa join aa.id.aula a join aa.id.aluno al where al.id = :idAluno")
    List<Long> getIdAulas(Long idAluno);

    @Query("select al from AlunoAula aa join aa.id.aula au join aa.id.aluno al where au.id = :idAula")
    List<Aluno> getAlunosAulas(Long idAula);

    @Query("select aa from AlunoAula aa join aa.id.aula au join aa.id.aluno al where au.ativo = :status and al.id = :idAluno and au.dataAula BETWEEN :dataInicio and :dataFim order by au.dataAula desc")
    List<AlunoAula> getAulasAlunoComData(Integer status, Long idAluno, LocalDateTime dataInicio, LocalDateTime dataFim);
}
