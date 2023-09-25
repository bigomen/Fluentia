package br.com.fluentia.repository;

import br.com.fluentia.model.Plano;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanoRepository extends BaseRepository<Plano>{

	List<Plano> findAllByAtivoAndPublicoOrderByNomeAsc(Integer ativo, Boolean publico);

	List<Plano> findAllByAtivoOrderByNomeAsc(Integer ativo);

	List<Plano> findAllByOrderByNomeAsc();

	Optional<Plano> findByCodigoIugu(String codigoIugu);

	List<Plano> findAllByIdEmpresaOrderByNomeAsc(Long idEmpresa);
	List<Plano> findAllByIdEmpresaAndAtivoOrderByNomeAsc(Long idEmpresa, Integer ativo);

	Boolean existsByNome(String nome);

	@Query("SELECT plan from Plano plan ORDER BY plan.nome")
	List<Plano> findAll();

	Plano findByNome(String nome);

	@Modifying
	@Query("update Plano p set p.ativo = :status where p.id = :id")
	void alterarStatus(Long id, Integer status);

	@Query("select CASE WHEN count(p.id) > 0 THEN true ELSE false END from Plano p where p.nome = :nome and p.id <> :id")
	Boolean validarNome(String nome, Long id);

	@Query("select p.codigoIugu from Plano p where p.id = :id")
	String getCodigo(Long id);

	@Query("select p.ativo from Plano p where p.id = :id")
	Integer getStatus(Long id);

	@Query("select p from Plano p where p.codigoIugu = null")
	List<Plano> planosSemIugu();
}