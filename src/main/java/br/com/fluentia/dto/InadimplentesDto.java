package br.com.fluentia.dto;

import java.time.LocalDate;

public class InadimplentesDto {

    private String nome;

    private String plano;

    private String empresa;

    private LocalDate dataVencimento;

    private Integer diasVencidos;

    public InadimplentesDto() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Integer getDiasVencidos() {
        return diasVencidos;
    }

    public void setDiasVencidos(Integer diasVencidos) {
        this.diasVencidos = diasVencidos;
    }
}
