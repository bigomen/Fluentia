package br.com.fluentia.dto;

import br.com.fluentia.model.Categoria;

import java.io.Serial;
import java.io.Serializable;

public class CategoriaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descricao;

    public Categoria dtoToModel(){
        Categoria model = new Categoria();
        model.setId(this.id);
        model.setDescricao(this.descricao);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
