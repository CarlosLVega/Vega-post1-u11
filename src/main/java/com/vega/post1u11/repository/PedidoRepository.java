package com.vega.post1u11.repository;

import com.vega.post1u11.model.Pedido;
import com.vega.post1u11.model.Producto;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Map<Long, Producto> PRODUCTOS = Map.of(
            1L, new Producto(1L, "Teclado mecanico", 120.0),
            2L, new Producto(2L, "Mouse ergonomico", 80.0),
            3L, new Producto(3L, "Monitor 24 pulgadas", 350.0)
    );

    default Producto findProductoById(Long id) {
        return PRODUCTOS.get(id);
    }
}
