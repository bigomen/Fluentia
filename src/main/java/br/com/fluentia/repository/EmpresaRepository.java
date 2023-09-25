package br.com.fluentia.repository;

import br.com.fluentia.model.Empresa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa>{

    Boolean existsByNome(String nome);

    @Query("select CASE WHEN count(e.id) > 0 THEN true ELSE false END from Empresa e where e.nome = :nome and e.id <> :id")
    Boolean validarNome(String nome, Long id);

    @Query("select new Empresa(e.id, e.nome) from Empresa e")
    List<Empresa> listaAtivos();

    @Query("select e.id from Empresa e")
    List<Long> listaIdEmpresas();
}
