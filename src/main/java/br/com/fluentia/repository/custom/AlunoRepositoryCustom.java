package br.com.fluentia.repository.custom;

import br.com.fluentia.dto.AulaFiltroDto;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.Aula;

import java.util.List;

public interface AlunoRepositoryCustom {

    List<Aula> getAulasAluno(Aluno aluno, AulaFiltroDto filtros);
}
