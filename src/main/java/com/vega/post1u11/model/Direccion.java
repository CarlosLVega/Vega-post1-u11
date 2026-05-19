package com.vega.post1u11.model;

import java.util.Objects;

public final class Direccion {

    private final String calle;
    private final String ciudad;
    private final String codigoPostal;

    public Direccion(String calle, String ciudad, String codigoPostal) {
        if (calle == null || calle.isBlank()) {
            throw new IllegalArgumentException("Calle requerida");
        }
        if (ciudad == null || ciudad.isBlank()) {
            throw new IllegalArgumentException("Ciudad requerida");
        }
        if (codigoPostal == null || codigoPostal.isBlank()) {
            throw new IllegalArgumentException("Codigo postal requerido");
        }
        this.calle = calle;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Direccion direccion)) {
            return false;
        }
        return Objects.equals(calle, direccion.calle)
                && Objects.equals(ciudad, direccion.ciudad)
                && Objects.equals(codigoPostal, direccion.codigoPostal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calle, ciudad, codigoPostal);
    }
}
