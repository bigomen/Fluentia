package br.com.fluentia.controller;

import br.com.fluentia.dto.AvaliacaoDto;
import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AvaliacaoController {

	@Autowired
	private AvaliacaoService avaliacaoService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/avaliacao/criar")
	public void criar(@RequestBody @Valid AvaliacaoDto dto){
		avaliacaoService.avaliacaoGeral(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/avaliacao/lista")
	public Page<AvaliacaoDto> lista(@RequestBody PaginacaoDto params){
		return avaliacaoService.lista(params);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/avaliacao/lista/mensal/{pagina}")
	public Page<AvaliacaoDto> listaMensal(@PathVariable Integer pagina){
		return avaliacaoService.listaMensal(pagina);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping({"/avaliacao/lista/mensal/porAluno", "/avaliacao/lista/mensal/porAluno/{idAluno}"})
	public List<AvaliacaoDto> listaMensalAluno(@PathVariable(required = false) Long idAluno){
		return avaliacaoService.listaMensalAluno(idAluno);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/avaliacao/lista/porAluno/{idAluno}")
	public List<AvaliacaoDto> porAluno(@PathVariable Long idAluno){
		return avaliacaoService.porAluno(idAluno);
	}

}
