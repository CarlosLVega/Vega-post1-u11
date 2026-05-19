package com.vega.post1u11.service;

import com.vega.post1u11.model.DatosCliente;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    public void notificarPedido(DatosCliente cliente, boolean urgente, String metodoPago) {
        System.out.println("Enviando email a: " + cliente.getEmail());
        System.out.println("Telefono del cliente: " + cliente.getTelefono());
        System.out.println("Direccion: " + cliente.getDireccion().getCalle() + ", "
                + cliente.getDireccion().getCiudad() + " - "
                + cliente.getDireccion().getCodigoPostal());
        System.out.println("Metodo de pago: " + metodoPago);
        System.out.println("Pedido urgente: " + urgente);
    }
}
