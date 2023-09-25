package br.com.fluentia.controller;

import br.com.fluentia.dto.PaginacaoDto;
import br.com.fluentia.dto.PreMatriculaDto;
import br.com.fluentia.service.PreMatriculaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PreMatriculaController {

    @Autowired
    private PreMatriculaService preMatriculaService;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/preMatricula/criar")
    public void criar(@RequestBody @Valid PreMatriculaDto dto) {
        preMatriculaService.criar(dto);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/preMatricula/{id}")
    public PreMatriculaDto findById(@PathVariable Long id) {
        return preMatriculaService.findById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/preMatricula/lista")
    public Page<PreMatriculaDto> lista(@RequestBody PaginacaoDto params) {
        return preMatriculaService.lista(params);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/preMatricula/emailProposta")
    public void gerarEmailProposta(@RequestBody Map<String, String> body) {
        preMatriculaService.gerarEmailProposta(body);
    }
}