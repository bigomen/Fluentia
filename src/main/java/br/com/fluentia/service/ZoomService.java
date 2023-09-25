package br.com.fluentia.service;

import br.com.fluentia.dto.ZoomDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class ZoomService extends AbstractService {

	@Value("${param.zoom.api-key}")
	private String apiKey;
	@Value("${param.zoom.api-secret}")
	private String apiSecret;
	@Value("${param.zoom.url}")
	private String zoomURL;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> createMeeting(ZoomDto zoomDto) {

		var url = zoomURL + zoomDto.getScheduleFor() + "/meetings";
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + generateZoomJWTTOken());
		headers.add("content-type", "application/json");
		
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(zoomDto.getMapZoomDto(),
				headers);
		ResponseEntity<Map> zEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
				Map.class);
		if (zEntity.getStatusCodeValue() == 201) {
			return zEntity.getBody();
		} 
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String,Object> findUsers() {

		var url = zoomURL;
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + generateZoomJWTTOken());
		headers.add("content-type", "application/json");
		
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(null,
				headers);
		ResponseEntity<Map> zEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				Map.class);
		if (zEntity.getStatusCodeValue() == 200) {
			return zEntity.getBody();
		} 
		return null;
	}

	private String generateZoomJWTTOken() {
		String id = UUID.randomUUID().toString().replace("-", "");
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		Date creation = new Date(System.currentTimeMillis());
		Date tokenExpiry = new Date(System.currentTimeMillis() + (1000 * 360));

		Key key = Keys.hmacShaKeyFor(apiSecret.getBytes());

		return Jwts.builder().setId(id).setIssuer(apiKey).setIssuedAt(creation).setSubject("")
				.setExpiration(tokenExpiry).signWith(key, signatureAlgorithm).compact();
	}

}
