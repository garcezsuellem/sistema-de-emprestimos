package br.com.empresa.emprestimos.shared.exception;

public class BusinessRuleViolationException extends BusinessException {

    public BusinessRuleViolationException(String message) {
        super(message);
    }
}
