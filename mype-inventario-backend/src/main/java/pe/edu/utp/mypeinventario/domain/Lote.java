package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Lote {
    private String id;
    private String proveedorId;
    private Integer cantidad;
    private String fechaIngreso;

    @com.fasterxml.jackson.annotation.JsonCreator
    public Lote() {
        this.id = UUID.randomUUID().toString();
        this.fechaIngreso = Instant.now().toString();
    }

    public Lote(String proveedorId, Integer cantidad, String fechaIngreso) {
        this();
        this.proveedorId = proveedorId;
        this.cantidad = cantidad;
        this.fechaIngreso = fechaIngreso;
        if (this.proveedorId == null || this.proveedorId.isBlank()) throw new IllegalArgumentException("proveedorId es obligatorio");
        if (this.cantidad == null || this.cantidad <= 0) throw new IllegalArgumentException("cantidad debe ser mayor a 0");
        if (this.fechaIngreso == null || this.fechaIngreso.isBlank()) this.fechaIngreso=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getProveedorId() { return proveedorId; }
    public void setProveedorId(String proveedorId) { this.proveedorId = proveedorId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getFechaIngreso() { return fechaIngreso; }

}
