package br.com.empresa.emprestimos.proposta;

import br.com.empresa.emprestimos.proposta.domain.service.CalculadoraEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.service.ItemPlanoPagamento;
import br.com.empresa.emprestimos.proposta.domain.service.PlanoPagamento;
import br.com.empresa.emprestimos.shared.exception.ValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalculadoraEmprestimoTest {

    private final CalculadoraEmprestimo calculadora = new CalculadoraEmprestimo();

    @Test
    void deveCalcularQuantidadeCorretaDeParcelas() {
        PlanoPagamento plano = calculadora.calcular(
                new BigDecimal("1000.00"), new BigDecimal("0.02"), 12, LocalDate.of(2026, 8, 1));

        assertThat(plano.parcelas()).hasSize(12);
    }

    @Test
    void todasAsParcelasDevemTerValorPositivo() {
        PlanoPagamento plano = calculadora.calcular(
                new BigDecimal("5000.00"), new BigDecimal("0.015"), 24, LocalDate.of(2026, 8, 1));

        for (ItemPlanoPagamento item : plano.parcelas()) {
            assertThat(item.valorTotal()).isGreaterThan(BigDecimal.ZERO);
            assertThat(item.valorPrincipal()).isGreaterThan(BigDecimal.ZERO);
            assertThat(item.valorJuros()).isGreaterThanOrEqualTo(BigDecimal.ZERO);
        }
    }

    @Test
    void somaDosPrincipaisDeveIgualarValorEmprestado() {
        BigDecimal valorPrincipal = new BigDecimal("10000.00");

        PlanoPagamento plano = calculadora.calcular(
                valorPrincipal, new BigDecimal("0.025"), 10, LocalDate.of(2026, 8, 1));

        BigDecimal somaPrincipais = plano.parcelas().stream()
                .map(ItemPlanoPagamento::valorPrincipal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(somaPrincipais).isEqualByComparingTo(valorPrincipal);
    }

    @Test
    void datasDeVencimentoDevemSerMensaisSequenciais() {
        LocalDate primeiroVencimento = LocalDate.of(2026, 8, 15);

        PlanoPagamento plano = calculadora.calcular(
                new BigDecimal("2000.00"), new BigDecimal("0.01"), 6, primeiroVencimento);

        for (int i = 0; i < plano.parcelas().size(); i++) {
            LocalDate esperado = primeiroVencimento.plusMonths(i);
            assertThat(plano.parcelas().get(i).vencimento()).isEqualTo(esperado);
        }
    }

    @Test
    void deveCalcularCorretamenteComTaxaZero() {
        PlanoPagamento plano = calculadora.calcular(
                new BigDecimal("1200.00"), BigDecimal.ZERO, 12, LocalDate.of(2026, 8, 1));

        for (ItemPlanoPagamento item : plano.parcelas()) {
            assertThat(item.valorJuros()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(item.valorTotal()).isEqualByComparingTo(new BigDecimal("100.00"));
        }
    }

    @Test
    void deveLancarExcecaoQuandoValorPrincipalForZeroOuNegativo() {
        assertThatThrownBy(() -> calculadora.calcular(
                BigDecimal.ZERO, new BigDecimal("0.02"), 12, LocalDate.now()))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void deveLancarExcecaoQuandoTaxaForNegativa() {
        assertThatThrownBy(() -> calculadora.calcular(
                new BigDecimal("1000.00"), new BigDecimal("-0.01"), 12, LocalDate.now()))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeParcelasForZeroOuNegativa() {
        assertThatThrownBy(() -> calculadora.calcular(
                new BigDecimal("1000.00"), new BigDecimal("0.02"), 0, LocalDate.now()))
                .isInstanceOf(ValidationException.class);
    }
}

