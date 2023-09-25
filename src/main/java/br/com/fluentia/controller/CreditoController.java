package br.com.fluentia.controller;

import br.com.fluentia.dto.CreditoDto;
import br.com.fluentia.service.CreditoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("credito")
public class CreditoController {

	@Autowired
	private CreditoService creditoService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/extrato/{idAluno}")
	public List<CreditoDto> extrato(@PathVariable Long idAluno){
		return creditoService.extrato(idAluno);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/credito/expirar/{idAluno}")
	public boolean verificaSeCreditoExpira(@PathVariable Long idAluno) {
		return creditoService.verificaSeCreditoExpira(idAluno);
	}
}