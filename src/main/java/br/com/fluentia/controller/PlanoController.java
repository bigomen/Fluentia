package br.com.fluentia.controller;

import br.com.fluentia.dto.PlanoDto;
import br.com.fluentia.service.PlanoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PlanoController {

	@Autowired
	private PlanoService planoService;

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/plano/criar")
	public void criar(@RequestBody @Valid PlanoDto dto){
		planoService.criar(dto);
	}

    @ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/admin/plano/editar")
	public void editar(@RequestBody @Valid PlanoDto dto){
		planoService.editar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/plano/listaAtivos")
	public List<PlanoDto> listarAtivos() {
		return planoService.listarAtivos();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/plano/listaAtivosPublico")
	public List<PlanoDto> listarAtivosPublico() {
		return planoService.listarAtivosPublico();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/plano/desativar/{idPlano}")
	public void desativar(@PathVariable Long idPlano){
		planoService.desativar(idPlano);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/plano/ativar/{idPlano}")
	public void ativar(@PathVariable Long idPlano){
		planoService.ativar(idPlano);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/plano/lista")
	public List<PlanoDto> buscarTodos(){
		return planoService.buscarTodos();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/plano/buscar/{id}")
	public PlanoDto buscar(@PathVariable Long id){
		return planoService.buscarPorId(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/plano/porEmpresa/{idEmpresa}")
	public List<PlanoDto> planosPorEmpresa(@PathVariable Long idEmpresa){
		return planoService.planosPorEmpresa(idEmpresa);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/admin/plano/novoValor")
	public void editarValorPlano(Map<String, String> body) {
		planoService.editarValorPlano(body);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/admin/plano/inserirTodosIugu")
	public void inserirTodosIugu(){
		planoService.inserirTodosIugu();
	}
}
