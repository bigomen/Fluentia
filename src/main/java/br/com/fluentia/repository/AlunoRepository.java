package br.com.fluentia.repository;

import br.com.fluentia.model.Aluno;
import br.com.fluentia.security.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends BaseRepository<Aluno>{

	Boolean existsByIdAndAtivo(Long id, Integer ativo);

	Optional<Aluno> findByCpf(String cpf);

	Page<Aluno> findByNomeLike(String nome, Pageable pageable);

	Boolean existsByEmail(String email);

	Boolean existsByNome(String nome);

	Optional<Aluno> findByEmailAndCpf(String email, String cpf);

	@Modifying
	@Query("update Aluno a set a.senha = :senha where a.id = :id")
	void atualizarSenha(String senha, Long id);

	@Query("select CASE WHEN count(a.id) > 0 THEN true ELSE false END from Aluno a where a.email = :email or a.nome = :nome or a.cpf = :cpf")
	Boolean validarDadosUnicosCadastro(String email, String nome, String cpf);
	
	@Query("SELECT aluno from Aluno aluno ORDER BY aluno.nome")
	List<Aluno> findAll();

	@Query("SELECT new Aluno(a.id, a.nome) FROM Aluno a WHERE a.ativo = 1")
	Collection<Aluno> findAlunosAtivos();
	
	Optional<Aluno> findByNome(String nome);
	Optional<Aluno> findByEmail(String email);

	@Modifying
	@Query("update Aluno a set a.ativo = :status where a.id = :id")
	void AlterarStatus(Long id, Integer status);

	@Query("select new Aluno(a.id, a.nome) from Aluno a where a.ativo = :status order by a.nome")
	List<Aluno> listarPorStatus(Integer status);

	@Query("select CASE WHEN count(a.id) > 0 THEN true ELSE false END from Aluno a where (a.cpf = :cpf or a.email = :email or a.nome = :nome) and a.id <> :id")
	Boolean validarAtualizacao(String cpf, String email, String nome, Long id);

	@Query("select a.ativo from Aluno a where a.id = :id")
	Integer statusAluno(Long id);

	@Query("select a.dataMatricula from Aluno a where a.id = :id")
	LocalDate getDataMatricula(Long id);

	@Query("select r from Role r where r.id = 1L")
	Role getRoles();

	@Query("select new Aluno(a.id, a.email, a.nome) from Aluno a where a.idIugu = null order by a.id")
	List<Aluno> listaAlunosSemIugu();

	@Modifying
	@Query("update Aluno a set a.idIugu = :idIugu where a.id = :idAluno")
	void updateIdIugu(Long idAluno, String idIugu);

	@Query("select new Aluno(a.id, a.cpf) from Aluno a")
	List<Aluno> getIdAndCpf();

	@Modifying
	@Query("update Aluno a set a.cpf = :cpf where a.id = :id")
	void atualizaCpf(String cpf, Long id);
}
