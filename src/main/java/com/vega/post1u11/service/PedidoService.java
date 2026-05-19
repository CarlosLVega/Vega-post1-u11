package com.vega.post1u11.service;

import com.vega.post1u11.model.CodigoDescuento;
import com.vega.post1u11.model.DatosCliente;
import com.vega.post1u11.model.LineaPedido;
import com.vega.post1u11.model.Pedido;
import com.vega.post1u11.model.Producto;
import com.vega.post1u11.repository.PedidoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository repo;
    private final NotificacionService notificacion;

    public PedidoService(PedidoRepository repo, NotificacionService notificacion) {
        this.repo = repo;
        this.notificacion = notificacion;
    }

    public String procesarPedido(Long clienteId, DatosCliente cliente, List<LineaPedido> lineas,
            String metodoPago, boolean esUrgente, CodigoDescuento descuento) {
        if (metodoPagoInvalido(metodoPago)) {
            return "ERROR_PAGO";
        }
        double total = aplicarDescuento(calcularTotal(lineas), descuento);
        notificacion.notificarPedido(cliente, esUrgente, metodoPago);
        return persistirPedido(clienteId, cliente, total);
    }

    private boolean metodoPagoInvalido(String metodoPago) {
        return metodoPago == null || metodoPago.isBlank();
    }

    private double calcularTotal(List<LineaPedido> lineas) {
        if (lineas == null || lineas.isEmpty()) {
            throw new IllegalArgumentException("El pedido requiere productos");
        }
        return lineas.stream()
                .mapToDouble(this::calcularSubtotal)
                .sum();
    }

    private double calcularSubtotal(LineaPedido linea) {
        Producto producto = repo.findProductoById(linea.getProductoId());
        if (producto == null) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        return producto.getPrecio() * linea.getCantidad();
    }

    private double aplicarDescuento(double total, CodigoDescuento descuento) {
        return descuento == null ? total : total * (1 - descuento.getPorcentaje());
    }

    private String persistirPedido(Long clienteId, DatosCliente cliente, double total) {
        if (clienteId == null) {
            return "ERROR_CLIENTE";
        }
        Pedido pedido = new Pedido(clienteId, cliente.getNombre(), total);
        return "OK_" + repo.save(pedido).getId();
    }
}
