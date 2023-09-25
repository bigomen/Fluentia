package br.com.fluentia.controller;

import br.com.fluentia.dto.NivelDto;
import br.com.fluentia.dto.SubnivelDto;
import br.com.fluentia.service.NivelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NivelController {

	@Autowired
	private NivelService nivelService;

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/nivel/criar")
	public void criar(@RequestBody @Valid NivelDto dto){
		nivelService.criar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/nivel/desativar/{idNivel}")
	public void desativar(@PathVariable Long idNivel){
		nivelService.desativar(idNivel);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/nivel/ativar/{idNivel}")
	public void ativar(@PathVariable Long idNivel){
		nivelService.ativar(idNivel);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/nivel/lista")
	public List<NivelDto> lista(){
		return nivelService.lista();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/nivel/listaAtivos")
	public List<NivelDto> buscarAtivos(){
		return nivelService.buscarAtivos();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/nivel/subnivel/{idAluno}")
	public List<SubnivelDto> buscarNivelSubnivelDoAluno(@PathVariable Long idAluno) {
		return nivelService.subnivelDoAluno(idAluno);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/nivel/subnivelInicial/{idAluno}")
	public String buscarNivelSubnivelInicialDoAluno(@PathVariable Long idAluno) {
		return nivelService.buscarNivelSubnivelInicialDoAluno(idAluno);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/nivel/porcentagemConcluida/{idAluno}")
	public List<SubnivelDto> porcentagemConcluida(@PathVariable Long idAluno) {
		return nivelService.progresso(idAluno);
	}
}
