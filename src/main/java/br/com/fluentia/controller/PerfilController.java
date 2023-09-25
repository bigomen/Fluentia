package br.com.fluentia.controller;

import br.com.fluentia.dto.PerfilDto;
import br.com.fluentia.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PerfilController {

	@Autowired
	private PerfilService perfilService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/perfil/criar")
	public void criar(@RequestBody @Valid PerfilDto dto){
		perfilService.salvar(dto);
	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/perfil/aluno/{idAluno}")
	public PerfilDto perfilAluno(@PathVariable Long idAluno){
		return perfilService.perfilAluno(idAluno);
	}

	
}
