package com.vega.post1u11.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.vega.post1u11.model.CodigoDescuento;
import com.vega.post1u11.model.DatosCliente;
import com.vega.post1u11.model.Direccion;
import com.vega.post1u11.model.LineaPedido;
import com.vega.post1u11.model.Pedido;
import com.vega.post1u11.model.Producto;
import com.vega.post1u11.repository.PedidoRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PedidoServiceTest {

    private PedidoRepository repository;
    private NotificacionServiceFake notificacion;
    private PedidoService service;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PedidoRepository.class);
        notificacion = new NotificacionServiceFake();
        service = new PedidoService(repository, notificacion);

        when(repository.findProductoById(1L)).thenReturn(new Producto(1L, "Teclado", 120.0));
        when(repository.findProductoById(2L)).thenReturn(new Producto(2L, "Mouse", 80.0));
        when(repository.save(any(Pedido.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void procesaPedidoValidoConDescuentoVip() {
        DatosCliente cliente = crearCliente();

        String resultado = service.procesarPedido(
                10L,
                cliente,
                List.of(new LineaPedido(1L, 1), new LineaPedido(2L, 2)),
                "TARJETA",
                true,
                CodigoDescuento.VIP10
        );

        assertThat(resultado).startsWith("OK_");
        assertThat(notificacion.clienteNotificado).isEqualTo(cliente);
        assertThat(notificacion.urgenteNotificado).isTrue();
        assertThat(notificacion.metodoPagoNotificado).isEqualTo("TARJETA");
    }

    @Test
    void rechazaClienteConEmailInvalidoDesdeValueObject() {
        Direccion direccion = new Direccion("Calle 1 # 2-3", "Bogota", "110111");

        assertThatThrownBy(() -> new DatosCliente(
                "Ana Perez",
                "ana.example.com",
                "3001234567",
                direccion
        )).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Email invalido");
    }

    @Test
    void rechazaMetodoPagoVacio() {
        String resultado = service.procesarPedido(
                10L,
                crearCliente(),
                List.of(new LineaPedido(1L, 1)),
                " ",
                false,
                null
        );

        assertThat(resultado).isEqualTo("ERROR_PAGO");
    }

    private DatosCliente crearCliente() {
        Direccion direccion = new Direccion("Calle 1 # 2-3", "Bogota", "110111");
        return new DatosCliente("Ana Perez", "ana@example.com", "3001234567", direccion);
    }

    private static final class NotificacionServiceFake extends NotificacionService {

        private DatosCliente clienteNotificado;
        private boolean urgenteNotificado;
        private String metodoPagoNotificado;

        @Override
        public void notificarPedido(DatosCliente cliente, boolean urgente, String metodoPago) {
            this.clienteNotificado = cliente;
            this.urgenteNotificado = urgente;
            this.metodoPagoNotificado = metodoPago;
        }
    }
}
