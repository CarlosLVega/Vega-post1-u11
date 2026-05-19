package com.vega.post1u11.service;

import com.vega.post1u11.model.Pedido;
import com.vega.post1u11.model.Producto;
import com.vega.post1u11.repository.PedidoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public String procesarPedido(Long clienteId, String clienteNombre,
            String clienteEmail, String clienteTelefono,
            String clienteDireccion, String clienteCiudad,
            String clienteCodigoPostal, List<Long> productosIds,
            List<Integer> cantidades, String metodoPago,
            boolean esUrgente, String codigoDescuento) {

        if (clienteId == null || clienteNombre == null
                || clienteNombre.isBlank() || clienteEmail == null
                || !clienteEmail.contains("@")) {
            return "ERROR_CLIENTE";
        }

        if (clienteTelefono == null || clienteTelefono.isBlank()
                || clienteDireccion == null || clienteDireccion.isBlank()
                || clienteCiudad == null || clienteCiudad.isBlank()
                || clienteCodigoPostal == null || clienteCodigoPostal.isBlank()) {
            return "ERROR_CLIENTE";
        }

        if (productosIds == null || cantidades == null
                || productosIds.isEmpty() || productosIds.size() != cantidades.size()) {
            return "ERROR_PRODUCTO";
        }

        double total = 0;
        for (int i = 0; i < productosIds.size(); i++) {
            Producto producto = repo.findProductoById(productosIds.get(i));
            if (producto == null || cantidades.get(i) == null || cantidades.get(i) <= 0) {
                return "ERROR_PRODUCTO";
            }
            total += producto.getPrecio() * cantidades.get(i);
        }

        if (codigoDescuento != null && codigoDescuento.equals("VIP10")) {
            total = total * 0.90;
        } else if (codigoDescuento != null && codigoDescuento.equals("NEW20")) {
            total = total * 0.80;
        }

        if (metodoPago == null || metodoPago.isBlank()) {
            return "ERROR_PAGO";
        }

        System.out.println("Enviando email a: " + clienteEmail);
        System.out.println("Telefono del cliente: " + clienteTelefono);
        System.out.println("Direccion: " + clienteDireccion + ", " + clienteCiudad
                + " - " + clienteCodigoPostal);
        System.out.println("Metodo de pago: " + metodoPago);
        System.out.println("Pedido urgente: " + esUrgente);

        Pedido pedido = new Pedido(clienteId, clienteNombre, total);
        return "OK_" + repo.save(pedido).getId();
    }
}
