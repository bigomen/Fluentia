package br.com.fluentia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> MethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        Map<String, String> error = new HashMap<>();
        error.put("error", errors.stream().findFirst().get().getField().concat(" é obrigatório"));
        return error;

    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Map<String, String> exception(Exception e){
        Map<String, String> error = new HashMap<>();
        if(e instanceof  BusinessException || e instanceof InternalErroException || e instanceof NotFoundException || e instanceof UniqueException){
            error.put("error", e.getMessage());
        }else if(e instanceof SQLException){
            error.put("error", "Ocorreu um erro no banco de dados, entre em contato com a equipe técnica!");
        }
        else {
            error.put("error", "Erro interno no sistema, entre em contato com a equipe técnica!");
        }
        return error;
    }
}
