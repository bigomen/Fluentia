package br.com.fluentia.service;

import br.com.fluentia.dto.EmailDto;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class EmailService {
	
	@Value("${param.mailgun.domain}")
	private String domainName;
	@Value("${param.mailgun.url}")
	private String urlService;
	@Value("${param.mailgun.private-api-key}")
	private String apiKey;
	
	public JsonNode sendMail(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
				.basicAuth("api", apiKey)
				.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
				.field("to", dto.getEmail())
				.field("subject", dto.getTitulo())
				.field("h:X-Mailgun-Variables", dto.getTexto())
				.field("template", "recovery")
				.asJson();

		return request.getBody();
	}
	public JsonNode sendAvaliacao(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
				.basicAuth("api", apiKey)
				.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
				.field("to", dto.getEmail())
				.field("subject", dto.getTitulo())
				.field("h:X-Mailgun-Variables", dto.getTexto())
				.field("template", "avaliacao")
				.asJson();
		return request.getBody();
	}
	
	public JsonNode sendMailAgendamento(EmailDto dto,File file) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
				.basicAuth("api", apiKey)
				.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
				.field("to", dto.getEmail())
				.field("subject", dto.getTitulo())
				.field("h:X-Mailgun-Variables", dto.getTexto())
				.field("template", "agendamento")
				.field("attachment", file)
				.asJson();

		return request.getBody();
	}
	public JsonNode sendMailCancelamento(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
				.basicAuth("api", apiKey)
				.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
				.field("to", dto.getEmail())
				.field("subject", dto.getTitulo())
				.field("h:X-Mailgun-Variables", dto.getTexto())
				.field("template", "cancelamento")
				.asJson();

		return request.getBody();
		
	}
	public JsonNode sendMailPosAula(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
						.basicAuth("api", apiKey)
						.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
						.field("to", dto.getEmail())
						.field("subject", dto.getTitulo())
						.field("h:X-Mailgun-Variables", dto.getTexto())
						.field("template", "posaula")
						.asJson();

		return request.getBody();
	}

	public void sendMailProposta(Map<String, String> body) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
				.basicAuth("api", apiKey)
				.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
				.field("to", body.get("email"))
				.field("subject", body.get("titulo"))
				.field("h:X-Mailgun-Variables", body.get("texto"))
				.field("template", "proposta")
				.asJson();

		request.getBody();
	}

	public JsonNode sendMailNovoPlano(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
				.basicAuth("api", apiKey)
				.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
				.field("to", dto.getEmail())
				.field("subject", dto.getTitulo())
				.field("h:X-Mailgun-Variables", dto.getTexto())
				.field("template", "novo-plano")
				.asJson();

		return request.getBody();
	}

	public JsonNode sendMailPausaAdesao(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
						.basicAuth("api", apiKey)
						.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
						.field("to", dto.getEmail())
						.field("subject", dto.getTitulo())
						.field("h:X-Mailgun-Variables", dto.getTexto())
						.field("template", "pausa-adesao")
						.asJson();

		return request.getBody();
	}

	public JsonNode sendMailCancelamentoAdesao(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
						.basicAuth("api", apiKey)
						.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
						.field("to", dto.getEmail())
						.field("subject", dto.getTitulo())
						.field("h:X-Mailgun-Variables", dto.getTexto())
						.field("template", "cancelamento-adesao")
						.asJson();

		return request.getBody();
	}

	public JsonNode sendMailNovoNivel(EmailDto dto) throws UnirestException {
		HttpResponse<JsonNode> request =
				Unirest.post(urlService + domainName + "/messages")
						.basicAuth("api", apiKey)
						.field("from", "Fluentia Learning Experience<contato@fluentia.com.br>")
						.field("to", dto.getEmail())
						.field("subject", dto.getTitulo())
						.field("h:X-Mailgun-Variables", dto.getTexto())
						.field("template", "novo-nivel")
						.asJson();

		return request.getBody();
	}

	public void enviarEmail(String email, String senhaGerada) throws UnirestException {
		var dto = new EmailDto();
		dto.setTitulo("Redefinição de Senha");
		dto.setEmail(email);
		dto.setTexto("{\"password\":\"" + senhaGerada + "\"}");

		sendMail(dto);
	}
}