package br.com.fluentia.repository;

import br.com.fluentia.model.Professor;
import br.com.fluentia.security.model.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends BaseRepository<Professor>{

	Boolean existsByNome(String nome);

	Boolean existsByEmail(String email);

	Boolean existsByCpf(String cpf);

	@Query("select r from Role r where r.id = 2L")
	Role getRoles();

	Optional<Professor> findByEmailAndCpf(String email, String cpf);

	@Modifying
	@Query("update Professor p set p.senha = :senha where p.id = :id")
	void atualizarSenha(String senha, Long id);

	@Query("select CASE WHEN count(p.id) > 0 THEN true ELSE false END from Professor p where p.nome = :nome and p.id <> :id")
	Boolean validarNome(String nome, Long id);

	@Query("select p.ativo from Professor p where p.id = :id")
	Integer statusProfessor(Long id);

	@Query("SELECT pr from Professor pr ORDER BY pr.nome")
	public List<Professor> findAll();
	
	@Query("SELECT pr from Professor pr WHERE pr.ativo=:iStatus ORDER BY pr.nome")
	public List<Professor> findByStatus(@Param("iStatus") Integer status);

	Optional<Professor> findByCpf(String cpf);

	Optional<Professor> findByNome(String nome);
	Optional<Professor> findByEmail(String email);

	@Modifying
	@Query("update Professor p set p.ativo = :status where p.id = :idProfessor")
	void alterarStatus(Long idProfessor, Integer status);

	@Query("select new Professor(p.id, p.nome) from Professor p where p.ativo = :status")
	List<Professor> listaPorStatus(Integer status);

	@Query("select p.idZoom from Professor p where p.id = :id")
	String getIdZoom(Long id);

//	@Query("select CASE WHEN count(p.id) > 0 from Professor p where p.nome = :nome and p.id <> :id")
//	private

	@Query("select new Professor(p.id, p.cpf) from Professor p")
	List<Professor> getIdAndCpf();

	@Modifying
	@Query("Update Professor p set p.cpf = :cpf where p.id = :id")
	void atualizarCpf(String cpf, Long id);
}
