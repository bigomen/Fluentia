package br.com.fluentia.controller;

import br.com.fluentia.dto.EmpresaDto;
import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/admin/empresa/criar")
    public void criarEmpresa(@RequestBody @Valid EmpresaDto dto){
        empresaService.criarEmpresa(dto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/admin/empresa/atualizar")
    public void atualizarEmpresa(@RequestBody @Valid EmpresaDto dto){
        empresaService.atualizarEmpresa(dto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/admin/empresa/apagar/{id}")
    public void apagarEmpresa(@PathVariable String id){
        empresaService.apagarEmpresa(Long.valueOf(id));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/admin/empresa/detalhes/{id}")
    public EmpresaDto detalhesEmpresa(@PathVariable String id){
        return empresaService.detalhesEmpresa(Long.valueOf(id));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/admin/empresa/lista")
    public Page<EmpresaDto> listaEmpresas(@RequestBody PaginacaoDto params){
        return empresaService.listaEmpresas(params);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/empresa/listaAtivos")
    public List<EmpresaDto> listaAtivos(){
        return empresaService.listaAtivos();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/empresa/listaSimples")
    public List<Long> listaSimples(){
        return empresaService.listaSimples();
    }

}
