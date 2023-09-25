package br.com.fluentia.dto;

import br.com.fluentia.model.Aluno;
import br.com.fluentia.utils.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;


public class EnderecoDto {

    @JsonProperty("street")
    private String rua;

    @JsonProperty("number")
    private String numero;

    @JsonProperty("complement")
    private String complemento;

    @JsonProperty("district")
    private String bairro;

    @JsonProperty("city")
    private String cidade;

    @JsonProperty("state")
    private String estado;

    @JsonProperty("country")
    private String pais;

    @JsonProperty("postalCode")
    private String cep;

    public static EnderecoDto obterEnderecoDto(Aluno aluno) {
        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.rua = aluno.getLogradouro();
        enderecoDto.numero = aluno.getNumero();
        enderecoDto.complemento = aluno.getComplemento();
        enderecoDto.bairro = "-";
        enderecoDto.cidade = aluno.getCidade();
        enderecoDto.estado = aluno.getUf();
        enderecoDto.pais = "BRA";
        enderecoDto.cep = NumberUtils.removeCaracterNaoNumerico(aluno.getCep());
        return enderecoDto;
    }

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
    
    
    
}
