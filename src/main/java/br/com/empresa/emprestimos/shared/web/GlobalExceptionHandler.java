package br.com.empresa.emprestimos.shared.web;

import br.com.empresa.emprestimos.shared.exception.BusinessRuleViolationException;
import br.com.empresa.emprestimos.shared.exception.DuplicateResourceException;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import br.com.empresa.emprestimos.shared.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(corpoErro(ex.getMessage()));
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(corpoErro(ex.getMessage()));
    }

    @ExceptionHandler({BusinessRuleViolationException.class, ValidationException.class})
    public ResponseEntity<Map<String, Object>> handleBusinessRule(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpoErro(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> campos = new HashMap<>();
        for (FieldError erro : ex.getBindingResult().getFieldErrors()) {
            campos.put(erro.getField(), erro.getDefaultMessage());
        }
        Map<String, Object> corpo = corpoErro("Erro de validação");
        corpo.put("campos", campos);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(corpo);
    }

    private Map<String, Object> corpoErro(String mensagem) {
        Map<String, Object> corpo = new HashMap<>();
        corpo.put("mensagem", mensagem);
        corpo.put("timestamp", OffsetDateTime.now());
        return corpo;
    }
}
