package br.com.fluentia.repository;

import br.com.fluentia.model.Nivel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NivelRepository extends BaseRepository<Nivel>{

	List<Nivel> findAllByOrderByNomeAsc();

	List<Nivel> findAllByAtivoOrderByNomeAsc(Integer ativo);

	Boolean existsByNome(String nome);

	@Query("select CASE WHEN count(n.id) > 0 THEN true ELSE false END from Nivel n where n.nome = :nome and n.id <> :id")
	Boolean validarNome(String nome, Long id);

	@Query("select n.ativo from Nivel n where n.id = :id")
	Integer statusNivel(Long id);

	@Query("SELECT naa from NivelAlunoAula naa WHERE naa.nome=:sNome")
	public Nivel findByName(@Param("sNome") String nome);
	@Query("SELECT naa from NivelAlunoAula naa WHERE naa.ativo=:iStatus")
	public List<Nivel> findByStatus(@Param("iStatus")Integer status);

	@Modifying
	@Query("update Nivel n set n.ativo = :status where n.id = :id")
	void alterarStatus(Long id, Integer status);
	
}
