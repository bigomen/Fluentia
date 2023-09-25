package br.com.fluentia.controller;

import br.com.fluentia.dto.*;
import br.com.fluentia.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class AlunoController {

	@Autowired
	private AlunoService alunoService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/aluno/criar")
	public void criarAluno(@RequestBody @Valid AlunoDto dto){
		alunoService.criarAluno(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PutMapping("/admin/aluno/atualizar")
	public void atualizar(@RequestBody @Valid AlunoDto dto){
		alunoService.atualizar(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/aluno/desativar/{id}")
	public void desativarAluno(@PathVariable Long id) {
		alunoService.desativar(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/admin/aluno/saldo/{idAluno}")
	public Integer saldo(@PathVariable Long idAluno){
		return alunoService.saldo(idAluno);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aluno/criarAlunoAssinatura")
	public void criarAlunoAssinatura(@RequestBody AlunoAssinaturaDto dto){
		alunoService.criarAlunoAssinatura(dto);
	}

	@ResponseStatus(HttpStatus.OK)
	@PutMapping("/aluno/atualizarMigracao")
	public void atualizarMigracao(@RequestBody AlunoAssinaturaDto dto){
		alunoService.atualizarAlunoMigracao(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/aluno/ativar/{id}")
	public void ativar(@PathVariable Long id){
		alunoService.ativar(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/aluno/lista")
	public Page<AlunoDto> lista(@RequestBody PaginacaoDto params) {
		return alunoService.lista(params);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/admin/aluno/porCpf/{cpf}")
	public AlunoDto porCpf(@PathVariable String cpf){
		return alunoService.porCpf(cpf);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/aluno/criarAuto")
	public Long criarAuto(@RequestBody  @Valid AlunoDto dto){
		return alunoService.criarAuto(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aluno/ativos")
	public List<AlunoDto> listarAtivos(){
		return alunoService.listarAtivos();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/admin/aluno/porId/{id}")
	public AlunoDto buscarPorId(@PathVariable Long id){
		return alunoService.porId(id);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@PostMapping("/admin/aluno/adicionarCreditos")
	public void addCreditos(@RequestBody CreditoDto dto){
		alunoService.addCreditos(dto);
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/aluno/me")
	public @ResponseBody AlunoDto buscarMe(){
		return alunoService.buscarMe();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping(value = "/aluno/report/{idAluno}")
	public List<ReportAlunoDto> reportAluno(@PathVariable Long idAluno){
		return alunoService.reportAluno(idAluno);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/admin/aluno/inserirTodosIugu")
	public List<Long> inserirTodosIugu(){
		return alunoService.inserirTodosIugu();
	}
}
