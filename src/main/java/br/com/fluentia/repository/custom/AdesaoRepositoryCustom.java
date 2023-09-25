package br.com.fluentia.repository.custom;

import br.com.fluentia.model.Adesao;

import java.util.Collection;
import java.util.Optional;

public interface AdesaoRepositoryCustom {

    Optional<Adesao> buscarAdesaoAtivaDoAluno(Long idAluno);

    Collection<Adesao> buscarAdesaoParaEnvioEmail();

    Collection<Adesao> buscarAdesaoParaReativar();
}
