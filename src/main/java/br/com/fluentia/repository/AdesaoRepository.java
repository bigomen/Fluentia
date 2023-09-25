package br.com.fluentia.repository;

import br.com.fluentia.dto.AdesaoCanceladaDto;
import br.com.fluentia.model.Adesao;
import br.com.fluentia.repository.custom.AdesaoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface AdesaoRepository extends JpaRepository<Adesao, String>, AdesaoRepositoryCustom
{
    @Query("SELECT new br.com.fluentia.dto.AdesaoCanceladaDto(a.codigo, a.dataCancelamento, a.motivoCancelamento, al.nome, p.nome) " +
            "FROM Adesao a JOIN a.aluno al JOIN a.plano p WHERE a.dataCancelamento IS NOT NULL")
    public Collection<AdesaoCanceladaDto> listaAdesaoCancelada();
}