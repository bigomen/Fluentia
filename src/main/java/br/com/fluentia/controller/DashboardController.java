package br.com.fluentia.controller;

import br.com.fluentia.dto.CardDto;
import br.com.fluentia.service.DashboardService;
import io.netty.handler.codec.http2.Http2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;
	
	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/dashboard")
	public Map<String,Object> getAll() throws Http2Exception {
		return dashboardService.cardAlunos();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/dashboard/nivel")
	public String getNivel() throws Http2Exception {
		return dashboardService.nivel();
	}

	@ResponseStatus(value = HttpStatus.OK)
	@GetMapping("/dashboard/{dataAula}")
	public List<CardDto> dashboard(@PathVariable String dataAula){
		return dashboardService.dashboard(dataAula);
	}

}
