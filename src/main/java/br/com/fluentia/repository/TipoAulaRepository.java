package br.com.fluentia.repository;

import br.com.fluentia.model.NivelAlunoAula;
import br.com.fluentia.model.TipoAula;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoAulaRepository extends BaseRepository<TipoAula>{

	Boolean existsByNome(String nome);

	List<TipoAula> findAllByOrderByNome();

	List<TipoAula> findAllByAtivoOrderByNome(Integer ativo);

	@Query("select CASE WHEN count(ta.id) > 0 THEN true ELSE false END from TipoAula ta where ta.nome = :nome and ta.id <> :id")
	Boolean validarNome(String nome, Long id);

	@Query("select ta.ativo from TipoAula ta where ta.id = :id")
	Integer statusTipoAula(Long id);

	@Modifying
	@Query("update TipoAula ta set ta.ativo = :status where ta.id = :id")
	void alterarStatus(Integer status, Long id);

	@Query("SELECT ta from TipoAula ta WHERE ta.nome=:sNome")
	public NivelAlunoAula findByName(@Param("sNome") String nome);
	@Query("SELECT ta from TipoAula ta WHERE ta.ativo=:iStatus")
	public List<TipoAula> findByStatus(@Param("iStatus")Integer status);
	
}
