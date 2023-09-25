package br.com.fluentia.dto;

import br.com.fluentia.model.Aluno;
import br.com.fluentia.utils.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DocumentoDto {

    @JsonProperty("type")
    private final String tipo = "CPF";

    @JsonProperty("value")
    private String cpf;

    public static List<DocumentoDto> obterDocumentosDto(Aluno aluno) {
        DocumentoDto documentoDto = new DocumentoDto();
        documentoDto.setCpf(NumberUtils.removeCaracterNaoNumerico(aluno.getCpf()));
        List<DocumentoDto> documentoDtoList = new ArrayList<>();
        documentoDtoList.add(documentoDto);
        return documentoDtoList;
    }

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTipo() {
		return tipo;
	}
    
    
}
