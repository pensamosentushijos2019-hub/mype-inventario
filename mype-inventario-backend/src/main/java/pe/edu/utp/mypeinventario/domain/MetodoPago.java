package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class MetodoPago {
    private String id;
    private String nombre;
    private Boolean activo = true;

    @com.fasterxml.jackson.annotation.JsonCreator
    public MetodoPago() {
        this.id = UUID.randomUUID().toString();
    }

    public MetodoPago(String nombre, Boolean activo) {
        this();
        this.nombre = nombre;
        this.activo = activo;
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public void desactivar() { this.activo=false; }
}
