package com.vega.post1u11.model;

public final class LineaPedido {

    private final Long productoId;
    private final int cantidad;

    public LineaPedido(Long productoId, int cantidad) {
        if (productoId == null) {
            throw new IllegalArgumentException("Producto requerido");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser positiva");
        }
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public int getCantidad() {
        return cantidad;
    }
}
