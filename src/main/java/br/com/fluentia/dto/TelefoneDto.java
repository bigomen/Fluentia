package br.com.fluentia.dto;

import br.com.fluentia.utils.NumberUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;


public class TelefoneDto {

    @JsonProperty("areaCode")
    private String ddd = "00";

    @JsonProperty("number")
    private String numero = "00000000";

    public TelefoneDto(String numero) {
        numero = NumberUtils.removeCaracterNaoNumerico(numero);
        if (StringUtils.isNotBlank(numero)) {
            this.ddd = numero.substring(0, 2);
            this.numero = numero.substring(2);
        }
    }

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
    
    
}
