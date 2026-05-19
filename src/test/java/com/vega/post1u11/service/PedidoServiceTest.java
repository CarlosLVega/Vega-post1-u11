package com.vega.post1u11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.vega.post1u11.model.Pedido;
import com.vega.post1u11.model.Producto;
import com.vega.post1u11.repository.PedidoRepository;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PedidoServiceTest {

    private PedidoRepository repository;
    private PedidoService service;

    @BeforeEach
    void setUp() throws Exception {
        repository = Mockito.mock(PedidoRepository.class);
        service = new PedidoService();

        Field repoField = PedidoService.class.getDeclaredField("repo");
        repoField.setAccessible(true);
        repoField.set(service, repository);

        when(repository.findProductoById(1L)).thenReturn(new Producto(1L, "Teclado", 120.0));
        when(repository.findProductoById(2L)).thenReturn(new Producto(2L, "Mouse", 80.0));
        when(repository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void procesaPedidoValidoConDescuentoVip() {
        String resultado = service.procesarPedido(
                10L,
                "Ana Perez",
                "ana@example.com",
                "3001234567",
                "Calle 1 # 2-3",
                "Bogota",
                "110111",
                List.of(1L, 2L),
                List.of(1, 2),
                "TARJETA",
                true,
                "VIP10"
        );

        assertThat(resultado).startsWith("OK_");
    }

    @Test
    void rechazaClienteConEmailInvalido() {
        String resultado = service.procesarPedido(
                10L,
                "Ana Perez",
                "ana.example.com",
                "3001234567",
                "Calle 1 # 2-3",
                "Bogota",
                "110111",
                List.of(1L),
                List.of(1),
                "TARJETA",
                false,
                null
        );

        assertThat(resultado).isEqualTo("ERROR_CLIENTE");
    }
}
