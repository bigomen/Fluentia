package br.com.fluentia.utils;

import br.com.fluentia.dto.PaginacaoDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class GeneratePageable {

    public static Pageable build(PaginacaoDto params, Sort defaultSort){
        Pageable pageable;
        int pagina = params.getFirst() / params.getRows();
        Sort sort;
        if(params.getSortField() != null){
            params.setSortField(params.getSortField().replace("[0]",""));
            if(params.getSortOrder() > 0){
                sort = Sort.by(params.getSortField()).ascending();
            }else {
                sort = Sort.by(params.getSortField()).descending();
            }

        }else {
            sort = defaultSort;
        }
        pageable = PageRequest.of(pagina, params.getRows(), sort);
        return pageable;
    }
}
