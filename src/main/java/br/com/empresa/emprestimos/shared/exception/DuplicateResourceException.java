package br.com.empresa.emprestimos.shared.exception;

public class DuplicateResourceException extends BusinessException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String entityName, String field, Object value) {
        super(String.format("%s já existe com %s: %s", entityName, field, value));
    }
}
