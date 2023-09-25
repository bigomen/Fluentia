package br.com.fluentia.repository;

import br.com.fluentia.model.PreMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreMatriculaRepository extends JpaRepository<PreMatricula, Long> {

    Boolean existsByNome(String nome);

    Boolean existsByEmail(String email);

    Optional<PreMatricula> findByEmail(String email);

    @Query("select CASE WHEN count(p.id) > 0 THEN true ELSE false END from PreMatricula p where p.nome = :nome and p.id <> :id")
    Boolean validarNome(String nome, Long id);

    @Query("select CASE WHEN count(p.id) > 0 THEN true ELSE false END from PreMatricula p where p.email = :email and p.id <> :id")
    Boolean validarEmail(String email, Long id);
}