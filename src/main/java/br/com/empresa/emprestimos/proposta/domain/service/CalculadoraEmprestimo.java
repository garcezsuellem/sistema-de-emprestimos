package br.com.empresa.emprestimos.proposta.domain.service;

import br.com.empresa.emprestimos.shared.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CalculadoraEmprestimo {

    private static final int ESCALA = 2;
    private static final MathContext PRECISAO = new MathContext(20);

    public PlanoPagamento calcular(BigDecimal valorPrincipal, BigDecimal taxaMensal,
                                   int quantidadeParcelas, LocalDate primeiroVencimento) {

        validarEntrada(valorPrincipal, taxaMensal, quantidadeParcelas, primeiroVencimento);

        BigDecimal valorParcela = calcularValorParcela(valorPrincipal, taxaMensal, quantidadeParcelas);

        List<ItemPlanoPagamento> itens = new ArrayList<>();
        BigDecimal saldoDevedor = valorPrincipal;

        for (int numero = 1; numero <= quantidadeParcelas; numero++) {
            BigDecimal valorJuros = saldoDevedor.multiply(taxaMensal, PRECISAO)
                    .setScale(ESCALA, RoundingMode.HALF_UP);

            BigDecimal valorPrincipalParcela = valorParcela.subtract(valorJuros)
                    .setScale(ESCALA, RoundingMode.HALF_UP);

            if (numero == quantidadeParcelas) {
                valorPrincipalParcela = saldoDevedor;
                valorParcela = valorPrincipalParcela.add(valorJuros);
            }

            saldoDevedor = saldoDevedor.subtract(valorPrincipalParcela);

            itens.add(new ItemPlanoPagamento(
                    numero,
                    primeiroVencimento.plusMonths(numero - 1L),
                    valorPrincipalParcela,
                    valorJuros,
                    valorParcela));
        }

        BigDecimal valorTotal = itens.stream()
                .map(ItemPlanoPagamento::valorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new PlanoPagamento(valorTotal, itens);
    }

    private BigDecimal calcularValorParcela(BigDecimal valorPrincipal, BigDecimal taxaMensal, int quantidadeParcelas) {
        if (taxaMensal.compareTo(BigDecimal.ZERO) == 0) {
            return valorPrincipal.divide(BigDecimal.valueOf(quantidadeParcelas), ESCALA, RoundingMode.HALF_UP);
        }

        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaMensal);
        BigDecimal fatorComposto = umMaisTaxa.pow(quantidadeParcelas, PRECISAO);

        BigDecimal numerador = valorPrincipal.multiply(taxaMensal, PRECISAO).multiply(fatorComposto, PRECISAO);
        BigDecimal denominador = fatorComposto.subtract(BigDecimal.ONE, PRECISAO);

        return numerador.divide(denominador, ESCALA, RoundingMode.HALF_UP);
    }

    private void validarEntrada(BigDecimal valorPrincipal, BigDecimal taxaMensal,
                                int quantidadeParcelas, LocalDate primeiroVencimento) {
        if (valorPrincipal == null || valorPrincipal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Valor principal deve ser maior que zero");
        }
        if (taxaMensal == null || taxaMensal.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Taxa de juros mensal não pode ser negativa");
        }
        if (quantidadeParcelas <= 0) {
            throw new ValidationException("Quantidade de parcelas deve ser maior que zero");
        }
        if (primeiroVencimento == null) {
            throw new ValidationException("Data do primeiro vencimento é obrigatória");
        }
    }
}
