package br.com.fluentia.controller;

import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.dto.ProfessorDto;
import br.com.fluentia.service.DashboardService;
import br.com.fluentia.service.ProfessorService;
import io.netty.handler.codec.http2.Http2Exception;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;
	@Autowired
	private DashboardService dashboardService;

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/professor/criar")
	public void criar(@RequestBody @Valid ProfessorDto dto){
		professorService.criar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/admin/professor/atualizar")
	public void atualizar(@RequestBody @Valid ProfessorDto dto){
		professorService.atualizar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/professor/desativar/{idProfessor}")
	public void desativar(@PathVariable Long idProfessor){
		professorService.desativar(idProfessor);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/professor/ativar/{idProfessor}")
	public void ativar(@PathVariable Long idProfessor) throws Http2Exception {
		professorService.ativar(idProfessor);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/professor/lista")
	public Page<ProfessorDto> lista(@RequestBody PaginacaoDto params){
		return professorService.lista(params);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/professor/listaAtivos")
	public List<ProfessorDto> listaAtivos(){
		return professorService.listaAtivos();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/professor/{cpf}")
	public ProfessorDto buscarPorCpf(@PathVariable String cpf){
		return professorService.buscarPorCpf(cpf);
	}

//	@Async
//	@RequestMapping(value = "/gerar-senha",method = RequestMethod.POST)
//	public @ResponseBody Long gerarSenha(@RequestBody ProfessorDto dto) throws HttpException {
//		return professorService.gerarLogin(dto);
//	}
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/professor/logado")
	public ProfessorDto logado(){
		return professorService.logado();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/professor/zoomId/{idProfessor}")
	public String buscarIdZoom(@PathVariable Long idProfessor){
		return professorService.buscarIdZoom(idProfessor);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/professor/listaZoom/{email}")
	public String listaZoom(@PathVariable String email){
		return professorService.listaZoomTeste(email);
	}
}
