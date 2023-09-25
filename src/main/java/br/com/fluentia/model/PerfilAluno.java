package br.com.fluentia.model;

import br.com.fluentia.dto.PerfilAlunoDto;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "perfil_aluno")
public class PerfilAluno implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data")
    private LocalDate data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_aluno", referencedColumnName = "id", updatable = false, insertable = false)
    private Aluno aluno;

    @Column(name = "fk_aluno")
    private Long idAluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id", updatable = false, insertable = false)
    private Categoria categoria;

    @Column(name = "fk_categoria")
    private Long idCategoria;

    @Column(name = "observacao")
    private String observacao;

    public PerfilAlunoDto dtoToModel(){
        PerfilAlunoDto dto = new PerfilAlunoDto();
        dto.setId(this.id);
        dto.setData(this.data);
        if(this.aluno != null){
            dto.setAluno(this.aluno.modelToDto());
        }
        dto.setIdAluno(this.idAluno);
        if(this.categoria != null){
            dto.setCategoria(this.categoria.modelToDto());
        }
        dto.setIdCategoria(this.idCategoria);
        dto.setObservacao(this.observacao);
        return dto;
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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
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
