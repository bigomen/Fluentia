package br.com.fluentia.dto;

import br.com.fluentia.model.PerfilAluno;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class PerfilAlunoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDate data;
    private AlunoDto aluno;
    private Long idAluno;
    private CategoriaDto categoria;
    private Long idCategoria;
    private String observacao;

    public PerfilAluno dtoToModel(){
        PerfilAluno model = new PerfilAluno();
        model.setId(this.id);
        model.setData(this.data);
        model.setAluno(null);
        model.setIdAluno(this.idAluno);
        model.setCategoria(null);
        model.setIdCategoria(this.idCategoria);
        model.setObservacao(this.observacao);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public AlunoDto getAluno() {
        return aluno;
    }

    public void setAluno(AlunoDto aluno) {
        this.aluno = aluno;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public CategoriaDto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDto categoria) {
        this.categoria = categoria;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
