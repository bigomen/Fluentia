package br.com.fluentia.repository;

import br.com.fluentia.model.Aula;
import br.com.fluentia.model.TipoAula;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AulaRepository extends BaseRepository<Aula>{
	
//	@Query("SELECT a FROM Aula a WHERE a.ativo = :ativ AND a.dataAula LIKE :dAula ORDER BY a.dataAula")
//	List<Aula> findByDataAulas(@Param("dAula") String dataAula,@Param("ativ") Integer ativo);
	
	@Query("SELECT a FROM Aula a WHERE a.ativo = :ativ AND a.id = :lId ")
	List<Aula> findById(@Param("lId") Long id,@Param("ativ") Integer ativo);

//	@Query("SELECT a FROM Aula a join a.alunos l WHERE a.ativo = 1 AND l.id = :alunoId ORDER BY a.dataAula desc")
//	List<Aula> findByAluno(@Param("alunoId") Long alunoId);
//
//
//	@Query("SELECT a FROM Aula a WHERE a.ativo = :ativ AND a.idProfessor=:idProf AND a.dataAula LIKE :dAula ORDER BY a.dataAula")
//	List<Aula> findByDataAulas(@Param("dAula") String dataAula,@Param("idProf") Long idProfessor ,@Param("ativ") Integer ativo);
//
//	@Query(value = "SELECT * FROM aula a WHERE a.ativo = 1 AND a.fk_professor=:idProf AND SUBSTR(a.data_aula,1,10) >= current_date() - INTERVAL 120 DAY ORDER BY a.data_aula desc", nativeQuery = true)
//	List<Aula> findFuturo(@Param("idProf") Long idProfessor);

//	@Query("SELECT a FROM Aula a WHERE a.ativo = 1 AND a.dataAula >= current_date ORDER BY a.dataAula")
//	List<Aula> findAllFuturo();

//	@Query(value = "SELECT * FROM aula a WHERE a.ativo = 1 AND SUBSTR(a.data_aula,1,10)>=current_date()- INTERVAL 120 DAY ORDER BY a.data_aula", nativeQuery = true)
//	List<Aula> findUltimosDias();

	@Query("select a.dataAula from Aula a where a.dataAula >= current_date order by a.dataAula asc")
	List<LocalDateTime> aulasFuturas();
	
	@Query("SELECT count(a) FROM Aula a WHERE a.idProfessor=:idProf AND a.idTipoAula = :tipoAula AND a.dataAula LIKE :dAula")
	Long countAula(@Param("dAula") String dataAula,@Param("idProf") Long idProfessor, @Param("tipoAula") Long idTipo);

	@Query("SELECT a FROM Aula a WHERE a.dataAula LIKE :dAula")
	List<Aula> findByDataAulas(@Param("dAula") String dataAula);
	
	@Modifying
	@Query("update Aula a set a.ativo = :status where a.id = :id")
	void alterarStatus(Long id, Integer status);

	@Query("select new Aula(a.id, a.nome) from Aula a where a.ativo = :status order by a.nome")
	List<Aula> listarPorStatus(Integer status);

	List<Aula> findAllByDataAulaAndAtivoOrderByDataAulaAsc(LocalDateTime dataAula, Integer ativo);

	@Query("select a from Aula a where a.ativo = :status and a.dataAula BETWEEN :inicioDia AND :fimDia order by a.dataAula asc")
	List<Aula> aulasPorData(Integer status, LocalDateTime inicioDia, LocalDateTime fimDia);

	@Query("select a from Aula a where a.ativo = :status and a.idProfessor = :idProfessor and a.dataAula BETWEEN :inicioDia AND :fimDia order by a.dataAula asc")
	List<Aula> aulasPorDataProfessor(Integer status, LocalDateTime inicioDia, LocalDateTime fimDia, Long idProfessor);

	@Query("select new Aula(a.id, a.dataAula, a.joinUrl, p.nome) from Aula a join a.professor p where a.dataAula BETWEEN :inicioDia AND :fimDia and a.ativo = :status")
	List<Aula> aulasRelatorio(Integer status, LocalDateTime inicioDia, LocalDateTime fimDia);

	@Query("select a from AlunoAula aa join aa.id.aula a join aa.id.aluno al where al.id = :idAluno and a.ativo = :status order by a.dataAula desc")
	List<Aula> aulasPorAluno(Long idAluno, Integer status);

	@Query("select a from AlunoAula aa join aa.id.aula a  join aa.id.aluno al where al.id = :idAluno and a.ativo = :status and a.dataAula BETWEEN :inicioDia AND :fimDia order by a.dataAula desc")
	List<Aula> aulasPorAlunoComData(Long idAluno, Integer status, LocalDateTime inicioDia, LocalDateTime fimDia);

	@Query("select a from Aula a where a.idProfessor = :idProfessor and a.ativo = :ativo and a.dataAula BETWEEN :dataInicio and :dataFim")
	List<Aula> getAulasPorProfessorData(Long idProfessor, Integer ativo, LocalDateTime dataInicio, LocalDateTime dataFim);

	List<Aula> findByIdProfessorAndAtivoOrderByDataAulaDesc(Long idProfessor, Integer ativo);

	@Query("select m.descricao from Aula a join a.materialList m where a.id = :idAula")
	List<String> getMaterialPorAula(Long idAula);

	@Modifying
	@Query("update Aula a set a.idProfessor = :idProfessor where a.id = :idAula")
	void trocarProfessor(Long idAula, Long idProfessor);

	@Query("select a.ativo from Aula a where a.id = :id")
	Integer statusAula(Long id);

	@Query("select a from Aula a where HOUR(a.dataAula) = :hora")
	List<Aula> aulasPorHora(Integer hora);

	@Query("select DISTINCT ta from Aula a join a.tipoAula ta where a.idProfessor = :id and a.dataAula BETWEEN :dataInicio and :dataFim")
	List<TipoAula> getTiposPorProfessor(Long id, LocalDateTime dataInicio, LocalDateTime dataFim);

	@Query("select count(a.id) from Aula a where a.idProfessor = :idProfessor and a.idTipoAula = :idTipo")
	Integer countAulaPorProfessorTipoAula(Long idTipo, Long idProfessor);

	@Query("select MAX(a.dataAula) from Aula a where a.credito = :credito and a.duracao = :duracao and a.idModalidade = :idModalidade and a.idProfessor = :idProfessor and a.idTipoAula = :idTipoAula and a.nome = :nome")
	LocalDateTime getDataUltimaAula(
			Integer credito,
			Integer duracao,
			Long idModalidade,
			Long idProfessor,
			Long idTipoAula,
			String nome
	);
}
