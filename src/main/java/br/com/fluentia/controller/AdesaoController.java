package br.com.fluentia.controller;

import br.com.fluentia.dto.AdesaoCanceladaDto;
import br.com.fluentia.dto.AlunoPlanoDto;
import br.com.fluentia.dto.FaturaDto;
import br.com.fluentia.dto.InadimplentesDto;
import br.com.fluentia.service.AdesaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
public class AdesaoController {

	@Autowired
	private AdesaoService adesaoService;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/adesao/buscarPlanoPorAluno")
	public AlunoPlanoDto buscarPlanoPorAlunoLogado() {
		return adesaoService.buscarPlanoPorAlunoLogado();
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/adesao/cancelar")
	public void cancelarAdesao(@RequestBody Map<String, String> body) {
		adesaoService.cancelarAdesao(body);
	}


	@ResponseStatus(HttpStatus.OK)
	@PutMapping({"/adesao/ativarDesativar", "/admin/adesao/ativarDesativar/{idAluno}"})
	public void suspenderReativarAdesao(@PathVariable(required = false) Long idAluno) {
		adesaoService.suspenderReativarAdesao(idAluno);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/adesao/listaCancelada")
	public Collection<AdesaoCanceladaDto> buscarAdesaoCancelada() {
		return adesaoService.buscarAdesaoCancelada();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/adesao/adicionarCartao")
	public void adicionarCartao(@RequestBody Map<String, String> body){
		adesaoService.adicionarCartao(body, null);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/adesao/alterarPlano")
	public void trocarCartao(@RequestBody Map<String, String> body){
		adesaoService.alterarPlano(body);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/adesao/gerarCobranca")
	public void gerarCobranca(@RequestBody Map<String, String> body){
		adesaoService.gerarCobranca(body);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/adesao/criarAssinatura")
	public void criarAssinatura(@RequestBody Map<String, String> body){
		adesaoService.criarAssinatura(body, null);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/admin/adesao/alunosInadimplentes")
	public List<InadimplentesDto> alunosInadimplentes(){
		return adesaoService.alunosInadimplentes();
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping({"/admin/adesao/fatura/{idAluno}", "/adesao/fatura"})
	public List<FaturaDto> fatura(@PathVariable(required = false) Long idAluno){
		return adesaoService.fatura(idAluno);
	}

}
