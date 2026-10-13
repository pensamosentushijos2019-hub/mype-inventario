package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Marca {
    private String id;
    private String nombre;

    @com.fasterxml.jackson.annotation.JsonCreator
    public Marca() {
        this.id = UUID.randomUUID().toString();
    }

    public Marca(String nombre) {
        this();
        this.nombre = nombre;
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

}
