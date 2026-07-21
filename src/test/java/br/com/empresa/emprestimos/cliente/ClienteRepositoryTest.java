package br.com.empresa.emprestimos.cliente;

import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.infrastructure.persistence.ClienteJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Testcontainers
class ClienteRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void configurarPropriedades(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> "false");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Autowired
    private ClienteJpaRepository clienteJpaRepository;

    @Test
    void deveSalvarEBuscarClientePorId() {
        Cliente cliente = new Cliente("João Silva", "111.444.777-35",
                LocalDate.of(1990, 1, 1), "joao@email.com", "11999998888");

        Cliente salvo = clienteJpaRepository.save(cliente);

        Optional<Cliente> encontrado = clienteJpaRepository.findById(salvo.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("João Silva");
    }

    @Test
    void deveBuscarClientePorCpf() {
        Cliente cliente = new Cliente("Maria Santos", "222.333.444-05",
                LocalDate.of(1985, 3, 20), "maria@email.com", "11988887777");

        clienteJpaRepository.save(cliente);

        Optional<Cliente> encontrado = clienteJpaRepository.findByCpf("222.333.444-05");

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("Maria Santos");
    }

    @Test
    void deveRetornarVazioQuandoCpfNaoExiste() {
        Optional<Cliente> encontrado = clienteJpaRepository.findByCpf("000.000.000-00");

        assertThat(encontrado).isEmpty();
    }

    @Test
    void deveVerificarExistenciaPorEmail() {
        Cliente cliente = new Cliente("Pedro Costa", "333.222.111-96",
                LocalDate.of(1992, 7, 10), "pedro@email.com", "11977776666");

        clienteJpaRepository.save(cliente);

        boolean existe = clienteJpaRepository.existsByEmail("pedro@email.com");
        boolean naoExiste = clienteJpaRepository.existsByEmail("naoexiste@email.com");

        assertThat(existe).isTrue();
        assertThat(naoExiste).isFalse();
    }
}