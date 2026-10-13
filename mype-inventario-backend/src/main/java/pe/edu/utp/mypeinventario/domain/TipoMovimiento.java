package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class TipoMovimiento {
    private String id;
    private String nombre;
    private String descripcion = "";

    @com.fasterxml.jackson.annotation.JsonCreator
    public TipoMovimiento() {
        this.id = UUID.randomUUID().toString();
    }

    public TipoMovimiento(String nombre, String descripcion) {
        this();
        this.nombre = nombre;
        this.descripcion = descripcion;
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}
