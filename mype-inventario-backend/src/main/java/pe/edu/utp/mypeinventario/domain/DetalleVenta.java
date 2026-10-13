package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class DetalleVenta {
    private String id;
    private String ventaId;
    private String varianteId;
    private Integer cantidad;
    private BigDecimal precioUnitario;

    @com.fasterxml.jackson.annotation.JsonCreator
    public DetalleVenta() {
        this.id = UUID.randomUUID().toString();
    }

    public DetalleVenta(String ventaId, String varianteId, Integer cantidad, BigDecimal precioUnitario) {
        this();
        this.ventaId = ventaId;
        this.varianteId = varianteId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        if (this.ventaId == null || this.ventaId.isBlank()) throw new IllegalArgumentException("ventaId es obligatorio");
        if (this.varianteId == null || this.varianteId.isBlank()) throw new IllegalArgumentException("varianteId es obligatorio");
        if (this.cantidad == null || this.cantidad <= 0) throw new IllegalArgumentException("cantidad debe ser mayor a 0");
        if (this.precioUnitario == null || this.precioUnitario.compareTo(BigDecimal.ZERO)<0) throw new IllegalArgumentException("precioUnitario debe ser un número válido");
    }

    public String getId() { return id; }

    public String getVentaId() { return ventaId; }
    public void setVentaId(String ventaId) { this.ventaId = ventaId; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal calcularSubtotal() { return precioUnitario.multiply(BigDecimal.valueOf(cantidad)); }
}
