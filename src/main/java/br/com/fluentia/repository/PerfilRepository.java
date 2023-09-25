package br.com.fluentia.repository;

import br.com.fluentia.model.Perfil;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends BaseRepository<Perfil>{

    Optional<Perfil> findByIdAluno(Long idAluno);
	
}
