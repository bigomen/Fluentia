package br.com.fluentia.controller;

import br.com.fluentia.dto.*;
import br.com.fluentia.service.AulaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class AulaController {

	@Autowired
	private AulaService aulaService;

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/criar")
	public Long criar(@RequestBody @Valid AulaDto dto){
		return aulaService.criar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/aula/atualizar")
	public void atualizar(@RequestBody @Valid AulaDto dto){
		aulaService.atualizar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/desativar/{id}")
	public void desativar(@PathVariable Long id){
		aulaService.desativar(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/lista")
	public Page<AulaDto> lista(@RequestBody PaginacaoDto params){
		return aulaService.lista(params);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/ativar/{id}")
	public void ativar(@PathVariable Long id){
		aulaService.ativar(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/buscarPorData/{dataAula}")
	public List<AulaDto> buscarPorData(@PathVariable LocalDate dataAula){
		return aulaService.buscarPorData(dataAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/buscarPorData/mensal/{dataAula}")
	public List<AulaDto> buscarPorDataMensal(@PathVariable String dataAula){
		return aulaService.buscarPorDataMensal(dataAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/buscarPorId/{id}")
	public AulaDto buscarPorId(@PathVariable Long id){
		return aulaService.buscarPorId(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/agendar")
	public void agendar(@RequestBody Map<String, Long> body){
		aulaService.agendar(body);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/cancelar")
	public void cancelar(@RequestBody Map<String, Long> body){
		aulaService.cancelar(body);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/buscarPorData/{dataAula}/modal")
	public List<AulaDto> buscarAtivosModal(@PathVariable LocalDate dataAula){
		return aulaService.buscarAtivosModal(dataAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping({"/aula/buscarPorAluno", "/aula/buscarPorAlunoComData/{dataAula}", "/aula/buscarPorAluno/{idAluno}/{dataAula}", "/aula/buscarPorAluno/{idAluno}"})
	public List<AulaDto> buscarAulasPorAluno(@PathVariable(required = false) Long idAluno, @PathVariable(required = false) LocalDate dataAula){
		return aulaService.buscarProAluno(idAluno, dataAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/criarZoom")
	public AulaDto criarZoom(@RequestBody Map<String, Long> body){
		return aulaService.criarZoom(body);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping({"/aula/buscarPorProfessor", "/aula/buscarPorProfessor/{dataAula}"})
	public List<AulaDto> buscarPorProfessor(@PathVariable(required = false) String dataAula){
		return aulaService.buscarPorProfessor(dataAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/buscarAlunos/{idAula}")
	public List<AlunoAulaDto> buscarAlunos(@PathVariable Long idAula){
		return aulaService.buscarAlunos(idAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/detalhe")
	public List<AulaDto> detalhe(@RequestBody AulaFiltroDto filtros){
		return aulaService.detalhe(filtros);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/aulaInDay")
	public Set<LocalDate> buscarAlunosDaAula(){
		return aulaService.buscarAulaDia();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aula/exportDiaAula/{dataAula}")
	public Map<String, String> buscarDadosAulaDia(@PathVariable LocalDate dataAula){
		return aulaService.generateReport(dataAula);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aula/confirmarPresenca")
	public void confirmarPresenca(@RequestBody AlunoPresencaDto dto){
		aulaService.confirmarPresenca(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PatchMapping("/aula/alterarProfessor/{idAula}/{idProfessor}")
	public void alterarProfessor(@PathVariable Long idAula, @PathVariable Long idProfessor) {
		aulaService.alterarProfessor(idAula, idProfessor);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/aula/recorrencia")
	public void aulaRecorrencia(@RequestBody Map<String, String> body){
		aulaService.recorrencia(body);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/admin/aula/recorrencia/lista")
	public List<AulaRecorrenciaDto> lista(){
		return aulaService.listaRecorrencia();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@DeleteMapping("/admin/aula/recorrencia/{id}")
	public void apagarRecorrencia(@PathVariable Long id){
		aulaService.apagarRecorrencia(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/admin/aula/testeRecorrencia")
	public void testeRecorrencia(){
		aulaService.testeRecorrencia();
	}
}
