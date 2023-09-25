package br.com.fluentia.repository.custom;

import br.com.fluentia.dto.AulaFiltroDto;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.Aula;
import br.com.fluentia.model.Nivel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class AlunoRepositoryCustomImpl implements AlunoRepositoryCustom{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Aula> getAulasAluno(Aluno aluno, AulaFiltroDto filtros) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Aula> criteria = builder.createQuery(Aula.class);
        Root<Aula> root = criteria.from(Aula.class);
        List<Predicate> predicates = new ArrayList<>();

        if(StringUtils.isNotBlank(filtros.getMes())){
            String ano = StringUtils.substringBefore(filtros.getMes(),"-");
            String mes = StringUtils.substringAfter(filtros.getMes(),"-");
            LocalDateTime dataInicio = LocalDateTime.of(Integer.parseInt(ano), Integer.parseInt(mes), 01, 00, 00);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.MONTH, Integer.parseInt(mes)-1);
            calendar.set(Calendar.YEAR, Integer.parseInt(ano));
            Integer diaFim = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            LocalDateTime dataFim = LocalDateTime.of(Integer.parseInt(ano), Integer.parseInt(mes), diaFim, 00, 00);
            predicates.add(builder.between(root.get("dataAula"), dataInicio, dataFim));
        }
        if(filtros.getSemana() != null){
            Expression<Integer> expression = builder.function("day_of_week", Integer.class, root.get("dataAula"));
            predicates.add(builder.equal(expression, filtros.getSemana()));
        }
        if(filtros.getHorario() != null){
            String hora = StringUtils.substringBefore(filtros.getHorario(), ":");
            String minuto = StringUtils.substringAfter(filtros.getHorario(), ":");
            Expression<Integer> expression = builder.function("hour", Integer.class, root.get("dataAula"));
            predicates.add(builder.equal(expression, Integer.parseInt(hora)));
            expression = builder.function("minute", Integer.class, root.get("dataAula"));
            predicates.add(builder.equal(expression, Integer.parseInt(minuto)));
        }
        if(filtros.getIdProfessor() != null){
            predicates.add(builder.equal(root.get("idProfessor"), filtros.getIdProfessor()));
        }
        if(filtros.getIdTipo() != null){
            predicates.add(builder.equal(root.get("idTipoAula"), filtros.getIdTipo()));
        }
        if(filtros.getIdNivel() != null){
            ListJoin<Aula, Nivel> joinNivel = root.joinList("nivelList");
            predicates.add(builder.equal(joinNivel.get("id"), filtros.getIdNivel()));
        }
        criteria.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteria).getResultList();
    }

}
