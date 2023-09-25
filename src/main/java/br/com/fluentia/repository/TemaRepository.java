package br.com.fluentia.repository;

import br.com.fluentia.model.Tema;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemaRepository extends BaseRepository<Tema>{

	Boolean existsByNome(String nome);

	@Query("select CASE WHEN count(t.id) > 0 THEN true ELSE false END from Tema t where t.nome = :nome and t.id <> :id")
	Boolean validarNome(String nome, Long id);

	@Query("select t.ativo from Tema t where t.id = :id")
	Integer statusTema(Long id);

	@Query("SELECT tema from Tema tema WHERE tema.ativo=:iStatus")
	List<Tema> findByStatus(@Param("iStatus")Integer status);

	@Modifying
	@Query("update Tema t set t.ativo = :status where t.id = :id")
	void alterarStatus(Long id, Integer status);

	@Query("select t from Tema t where t.ativo = :status")
	List<Tema> listaPorStatus(Integer status);
}
