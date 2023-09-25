package br.com.fluentia.controller;

import br.com.fluentia.dto.TemaDto;
import br.com.fluentia.service.TemaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TemaController {

	@Autowired
	private TemaService temaService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/tema/criar")
	public void criar(@RequestBody @Valid TemaDto dto){
		temaService.criar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/tema/desativar/{idTema}")
	public void desativar(@PathVariable Long idTema){
		temaService.desativar(idTema);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/tema/ativar/{idTema}")
	public void ativar(@PathVariable Long idTema){
		temaService.ativar(idTema);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/tema/lista")
	public List<TemaDto> lista(){
		return temaService.lista();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/tema/listaAtivos")
	public List<TemaDto> listaAtivos(){
		return temaService.listaAtivos();
	}
	
	

}
