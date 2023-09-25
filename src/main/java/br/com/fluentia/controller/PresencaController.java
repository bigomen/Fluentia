package br.com.fluentia.controller;

import br.com.fluentia.dto.RelatorioFiltroDto;
import br.com.fluentia.dto.RelatorioPresencaComFrequenciaDto;
import br.com.fluentia.service.PresencaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PresencaController {

	@Autowired
	private PresencaService presencaService;

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/presenca/relatorio")
	public RelatorioPresencaComFrequenciaDto relatorio(RelatorioFiltroDto params){
		return presencaService.relatorio(params);
	}
}
