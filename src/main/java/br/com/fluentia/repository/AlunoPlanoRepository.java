package br.com.fluentia.repository;

import br.com.fluentia.model.AlunoPlano;
import br.com.fluentia.model.Plano;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoPlanoRepository extends BaseRepository<AlunoPlano>{

    AlunoPlano findByCodigo(String codigo);

    AlunoPlano findByIdAluno(Long idAluno);

    Optional<AlunoPlano> findByIdAlunoAndIdPlano(Long idAluno, Long idPlano);

    @Query("select p from AlunoPlano ap join ap.plano p where ap.idAluno = :idAluno")
    Plano getPlanoAluno(Long idAluno);

    @Modifying
    @Query("DELETE FROM AlunoPlano ap where ap.codigo = :codigo")
    void apagarPorCodigo(String codigo);

    @Query("select ap from AlunoPlano ap join ap.aluno a where a.email = :email")
    AlunoPlano findByEmailAluno(String email);
}
