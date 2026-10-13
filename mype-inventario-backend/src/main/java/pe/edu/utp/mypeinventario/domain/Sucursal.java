package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Sucursal {
    private String id;
    private String comercianteId;
    private String nombre;
    private String direccion = "";
    private Boolean activa = true;

    @com.fasterxml.jackson.annotation.JsonCreator
    public Sucursal() {
        this.id = UUID.randomUUID().toString();
    }

    public Sucursal(String comercianteId, String nombre, String direccion, Boolean activa) {
        this();
        this.comercianteId = comercianteId;
        this.nombre = nombre;
        this.direccion = direccion;
        this.activa = activa;
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

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }

    public void actualizarDireccion(String nuevaDireccion) { if (nuevaDireccion == null || nuevaDireccion.isBlank()) throw new IllegalArgumentException("nuevaDireccion es obligatoria"); this.direccion=nuevaDireccion; }
    public void cerrar() { this.activa=false; }
}
