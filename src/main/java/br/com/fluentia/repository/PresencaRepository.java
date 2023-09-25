package br.com.fluentia.repository;

import br.com.fluentia.model.Presenca;
import org.springframework.stereotype.Repository;

@Repository
public interface PresencaRepository extends BaseRepository<Presenca> {

//    @Query("SELECT new br.com.fluentia.dto.RelatorioPresencaDto(a.id, a.dataAula, pr.nome, CASE WHEN(p.id IS NOT NULL) THEN true ELSE false END) " +
//            "FROM Aula a " +
//            "JOIN a.alunos al " +
//            "LEFT JOIN Presenca p on p.idAluno = al.id and p.idAula = a.id " +
//            "LEFT JOIN Professor pr on pr.id = a.idProfessor " +
//            "WHERE al.id = :alunoId")
//    Collection<RelatorioPresencaDto> relatorioPresenca(Long alunoId);
}
