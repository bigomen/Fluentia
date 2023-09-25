package br.com.fluentia.controller;

import br.com.fluentia.dto.TipoAulaDto;
import br.com.fluentia.service.TipoAulaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TipoAulaController {

	@Autowired
	private TipoAulaService tipoAulaService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/tipoAula/criar")
	public void criarAluno(@RequestBody @Valid TipoAulaDto dto){
		tipoAulaService.criar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/tipoAula/desativar/{idTipoAula}")
	public void desativarAluno(@PathVariable Long idTipoAula){
		tipoAulaService.desativar(idTipoAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/tipoAula/ativar/{idTipoAula}")
	public void ativarAluno(@PathVariable Long idTipoAula){
		tipoAulaService.ativar(idTipoAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/tipoAula/lista")
	public List<TipoAulaDto> buscarTodos(){
		return tipoAulaService.buscarTodos();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/tipoAula/listaAtivos")
	public List<TipoAulaDto> buscarAtivos(){
		return tipoAulaService.buscarAtivos();
	}

}
