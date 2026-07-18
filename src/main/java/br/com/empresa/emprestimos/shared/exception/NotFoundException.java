package br.com.empresa.emprestimos.shared.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(String entityName, Object id) {
        super(String.format("%s não encontrado(a) com id: %s", entityName, id));
    }
}
