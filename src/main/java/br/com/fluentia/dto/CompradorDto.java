package br.com.fluentia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class CompradorDto {

    @JsonProperty("name")
    private String nome;

    @JsonProperty("email")
    private String email;

    @JsonProperty("ip")
    private String ip;

    @JsonProperty("phone")
    private TelefoneDto telefoneDto;

    @JsonProperty("address")
    private EnderecoDto enderecoDto;

    @JsonProperty("documents")
    private List<DocumentoDto> documentosDto;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public TelefoneDto getTelefoneDto() {
		return telefoneDto;
	}

	public void setTelefoneDto(TelefoneDto telefoneDto) {
		this.telefoneDto = telefoneDto;
	}

	public EnderecoDto getEnderecoDto() {
		return enderecoDto;
	}

	public void setEnderecoDto(EnderecoDto enderecoDto) {
		this.enderecoDto = enderecoDto;
	}

	public List<DocumentoDto> getDocumentosDto() {
		return documentosDto;
	}

	public void setDocumentosDto(List<DocumentoDto> documentosDto) {
		this.documentosDto = documentosDto;
	}
    
}