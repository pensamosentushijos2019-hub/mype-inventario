package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Almacen {
    private String id;
    private String comercianteId;
    private String nombre;
    private String direccion = "";

    @com.fasterxml.jackson.annotation.JsonCreator
    public Almacen() {
        this.id = UUID.randomUUID().toString();
    }

    public Almacen(String comercianteId, String nombre, String direccion) {
        this();
        this.comercianteId = comercianteId;
        this.nombre = nombre;
        this.direccion = direccion;
        if (this.comercianteId == null || this.comercianteId.isBlank()) throw new IllegalArgumentException("comercianteId es obligatorio");
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
    }

    public String getId() { return id; }

    public String getComercianteId() { return comercianteId; }
    public void setComercianteId(String comercianteId) { this.comercianteId = comercianteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

}
