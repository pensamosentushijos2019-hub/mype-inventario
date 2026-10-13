package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class MovimientoInventario {
    private String id;
    private String varianteId;
    private String tipo;
    private Integer cantidad;
    private String motivo = "";
    private String fecha;

    @com.fasterxml.jackson.annotation.JsonCreator
    public MovimientoInventario() {
        this.id = UUID.randomUUID().toString();
        this.fecha = Instant.now().toString();
    }

    public MovimientoInventario(String varianteId, String tipo, Integer cantidad, String motivo, String fecha) {
        this();
        this.varianteId = varianteId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.fecha = fecha;
        if (this.varianteId == null || this.varianteId.isBlank()) throw new IllegalArgumentException("varianteId es obligatorio");
        if (!Set.of("entrada","salida").contains(this.tipo)) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: entrada, salida");
        if (this.cantidad == null || this.cantidad <= 0) throw new IllegalArgumentException("cantidad debe ser mayor a 0");
        this.fecha=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getFecha() { return fecha; }

    public int cantidadConSigno() { return "entrada".equals(tipo) ? cantidad : -cantidad; }
}
