package br.com.fluentia.dto;

import br.com.fluentia.model.Empresa;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serial;
import java.io.Serializable;

public class EmpresaDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "Nome")
    private String nome;
    private String cep;
    private String cidade;
    private String complemento;
    private String logradouro;
    private String numero;

    public Empresa dtoToModel(){
        Empresa model = new Empresa();
        model.setId(this.id);
        model.setNome(this.nome);
        model.setCep(this.cep);
        model.setCidade(this.cidade);
        model.setComplemento(this.complemento);
        model.setLogradouro(this.logradouro);
        model.setNumero(this.numero);
        return model;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
