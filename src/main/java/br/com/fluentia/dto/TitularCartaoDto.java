package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TitularCartaoDto {

    @JsonProperty("name")
    private String nome;

    @JsonProperty("birthDate")
    private String dataNascimento;

    @JsonProperty("documents")
    private List<DocumentoDto> documentosDto;

    @JsonProperty("phone")
    private TelefoneDto telefoneDto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public List<DocumentoDto> getDocumentosDto() {
        return documentosDto;
    }

    public void setDocumentosDto(List<DocumentoDto> documentosDto) {
        this.documentosDto = documentosDto;
    }

    public TelefoneDto getTelefoneDto() {
        return telefoneDto;
    }

    public void setTelefoneDto(TelefoneDto telefoneDto) {
        this.telefoneDto = telefoneDto;
    }
}
