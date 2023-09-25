package br.com.fluentia.service;

import br.com.fluentia.dto.FaturaDto;
import br.com.fluentia.exception.InternalErroException;
import br.com.fluentia.model.Aluno;
import br.com.fluentia.model.PlanoIugu;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class IuguService {

    @Value("${iugu.api.token}")
    private String token;

    public String criarPlanoIugu(PlanoIugu iugu){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(iugu);
        }catch (Exception e){
            throw new InternalErroException();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/plans?api_token=" + this.token))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        Map responseBody = g.fromJson(response.body(), Map.class);
        return (String) responseBody.get("id");
    }

    public void removerPlanoIugu(String codigo){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/plans/"+ codigo +"?api_token=" + this.token))
                .header("accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public void atualizarPlanoIugu(Map dto){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        }catch (Exception e){
            throw new InternalErroException();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/plans/" + dto.get("id") + "?api_token=" + this.token))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .method("PUT", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public Map buscarPlanoIugu(String codigo){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/plans/"+ codigo +"?api_token="+ this.token))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        return g.fromJson(response.body(), Map.class);
    }

    public String criarAssinaturaIugu(Map assinatura){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(assinatura);
        }catch (Exception e){
            throw new InternalErroException();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/subscriptions?api_token=" + this.token))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        Map responseBody = g.fromJson(response.body(), Map.class);
        return (String) responseBody.get("id");
    }

    public void alterarPlanoAssinatura(String idAssinatura, String identificadorPlano){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/subscriptions/"+ idAssinatura +"/change_plan/"+ identificadorPlano +"?api_token=" + this.token))
                .header("accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public void cobrancaDireta(Map body){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
        }catch (Exception e){
            throw new InternalErroException();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/charge?api_token="+ this.token))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public String criarClienteIugu(Aluno aluno){
        Map<String, String> cliente = new HashMap<>();
        cliente.put("email", aluno.getEmail());
        cliente.put("name", aluno.getNome());

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(cliente);
        }catch (Exception e){
            throw new InternalErroException();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/customers?api_token=" + this.token))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()) throw new InternalErroException();

        Gson g = new Gson();
        Map responseBody = g.fromJson(response.body(), Map.class);
        return (String) responseBody.get("id");
    }

    public void removerClienteIugu(String idCliente){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/customers/"+ idCliente +"?api_token=" + this.token))
                .header("accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()) throw new InternalErroException();
    }

    public void inserirFormaPagamento(Map<String, String> paymentBody){
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(paymentBody);
        }catch (Exception e){
            throw new InternalErroException();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/customers/"+ paymentBody.get("customer_id") +"/payment_methods?api_token=" + this.token))
                .header("accept", "application/json")
                .header("content-type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public void suspenderAssinatura(String codigo){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/subscriptions/"+ codigo +"/suspend?api_token=" + this.token))
                .header("accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public void reativarAssinatura(String codigo){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/subscriptions/"+ codigo +"/activate?api_token=" + this.token))
                .header("accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public void cancelarAssinatura(String codigo){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/subscriptions/"+ codigo +"?api_token="+ this.token))
                .header("accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
    }

    public Boolean verificarCartao(String codigo){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/customers/"+ codigo +"?api_token="+ this.token))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }if(response.statusCode() == HttpStatus.NOT_FOUND.value()){
            return false;

        }else if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        Map responseBody = g.fromJson(response.body(), Map.class);
        return responseBody.get("payment_methods") != null;
    }

    public String getCartao(String codigo){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/customers/"+ codigo +"/payment_methods?api_token="+ this.token))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        List<Map> responseBody = g.fromJson(response.body(), List.class);
        return (String) responseBody.stream().findFirst().get().get("id");
    }

    public List<String> getAlunosSuspensos(){

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/subscriptions?status_filter=suspended&api_token=" + this.token))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        Map responseBody = g.fromJson(response.body(), Map.class);
        List<Map> items = (List<Map>) responseBody.get("items");
        if(!items.isEmpty()){
            List<String> alunos = new ArrayList<>();
            for(Map i : items){
                List<Map> recentInvoices = (List<Map>) i.get("recent_invoices");
                Optional<Map> lastInvoice = recentInvoices.stream().findFirst();
                if(lastInvoice.isPresent()){
                    String status = (String) lastInvoice.get().get("status");
                    if(Objects.equals(status, "canceled") || Objects.equals(status, "expired")){
                        alunos.add((String) i.get("customer_email"));
                    }
                }
            }
            return alunos;
        }
        return new ArrayList<>();
    }

    public List<FaturaDto> buscarFatura(String idIugu){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.iugu.com/v1/invoices?customer_id="+ idIugu +"&api_token=" + this.token))
                .header("accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response;
        try {
            response  = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            throw new InternalErroException();
        }
        if(response.statusCode() != HttpStatus.OK.value()){
            throw new InternalErroException();
        }
        Gson g = new Gson();
        Map responseBody = g.fromJson(response.body(), Map.class);
        List<Map> items = (List<Map>) responseBody.get("items");
        if(!items.isEmpty()){
            List<FaturaDto> faturaDtos = new ArrayList<>();
            for(Map item : items){
                List<Map> faturas = (List<Map>) item.get("items");
                if(!faturas.isEmpty()){
                    for(Map i : faturas){
                        FaturaDto dto = new FaturaDto();
                        dto.setProduto((String) i.get("description"));
                        dto.setData((String) i.get("updated_at"));
                        dto.setValor((String) i.get("price"));
                        dto.setStatus((String) item.get("status"));
                        if(Objects.equals(dto.getStatus(), "refunded")){
                            String paidAt = (String) item.get("paid_at");
                            if(Objects.nonNull(paidAt)){
                                dto.setStatus("paid");
                            }
                            FaturaDto out = new FaturaDto();
                            out.setStatus("refunded");
                            out.setProduto("Estorno - ".concat(dto.getProduto()));
                            out.setData((String) item.get("refunded_at_iso"));
                            out.setValor((String) item.get("total_refunded"));
                            faturaDtos.add(out);
                        }
                        faturaDtos.add(dto);
                    }
                }
            }
            return faturaDtos;
        }
        return new ArrayList<>();
    }
}
