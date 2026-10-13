package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class DescuentoPromocion {
    private String id;
    private String varianteId;
    private BigDecimal porcentaje;
    private String fechaInicio;
    private String fechaFin;

    @com.fasterxml.jackson.annotation.JsonCreator
    public DescuentoPromocion() {
        this.id = UUID.randomUUID().toString();
    }

    public DescuentoPromocion(String varianteId, BigDecimal porcentaje, String fechaInicio, String fechaFin) {
        this();
        this.varianteId = varianteId;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        if (this.varianteId == null || this.varianteId.isBlank()) throw new IllegalArgumentException("varianteId es obligatorio");
        if (this.porcentaje == null || this.porcentaje.compareTo(BigDecimal.ZERO)<=0 || this.porcentaje.compareTo(BigDecimal.valueOf(100))>0) throw new IllegalArgumentException("porcentaje debe estar entre 0 y 100");
        if (this.fechaInicio == null || this.fechaInicio.isBlank() || this.fechaFin == null || this.fechaFin.isBlank()) throw new IllegalArgumentException("fechaInicio y fechaFin son obligatorias");
    }

    public String getId() { return id; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }

    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }

    public String getFechaFin() { return fechaFin; }
    public void setFechaFin(String fechaFin) { this.fechaFin = fechaFin; }

    public boolean estaActivo(String fecha) { Instant f=Instant.parse(fecha); return !f.isBefore(Instant.parse(fechaInicio)) && !f.isAfter(Instant.parse(fechaFin)); }
    public BigDecimal aplicarA(BigDecimal precioBase) { return precioBase.subtract(precioBase.multiply(porcentaje).divide(BigDecimal.valueOf(100))); }
}
