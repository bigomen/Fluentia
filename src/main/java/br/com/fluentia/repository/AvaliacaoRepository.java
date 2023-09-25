package br.com.fluentia.repository;

import br.com.fluentia.model.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends BaseRepository<Avaliacao>{

	@Query(value = "SELECT v.* FROM avaliacao v JOIN aula a on a.id = v.id_aula WHERE a.ativo = 1 AND SUBSTR(a.data_aula,1,10)>=current_date()- INTERVAL 90 DAY ORDER BY a.data_aula", nativeQuery = true)
	List<Avaliacao> findUltimosDias();

	Page<Avaliacao> findAllByMesAvaliacaoIsNotNull(Pageable pageable);

	@Query("select a from Avaliacao a where a.idAluno = :idAluno and a.mesAvaliacao <> null order by a.mesAvaliacao desc")
	List<Avaliacao> avaliacoesMensalAluno(Long idAluno);

	List<Avaliacao> findAllByIdAlunoOrderByMesAvaliacaoDesc(Long idAluno);
}
