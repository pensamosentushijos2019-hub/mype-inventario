package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class VarianteSKU {
    private String id;
    private String productoId;
    private String codigoBarra;
    private String talla;
    private String color;
    private Integer stockActual = 0;
    private Integer stockMinimo = 5;
    private BigDecimal precioVenta;

    @com.fasterxml.jackson.annotation.JsonCreator
    public VarianteSKU() {
        this.id = UUID.randomUUID().toString();
    }

    public VarianteSKU(String productoId, String codigoBarra, String talla, String color, Integer stockActual, Integer stockMinimo, BigDecimal precioVenta) {
        this();
        this.productoId = productoId;
        this.codigoBarra = codigoBarra;
        this.talla = talla;
        this.color = color;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.precioVenta = precioVenta;
        if (this.productoId == null || this.productoId.isBlank()) throw new IllegalArgumentException("productoId es obligatorio");
        if (this.codigoBarra == null || this.codigoBarra.isBlank()) throw new IllegalArgumentException("codigoBarra es obligatorio");
        if (this.precioVenta == null || this.precioVenta.compareTo(BigDecimal.ZERO)<0) throw new IllegalArgumentException("precioVenta debe ser un número válido");
    }

    public String getId() { return id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getCodigoBarra() { return codigoBarra; }
    public void setCodigoBarra(String codigoBarra) { this.codigoBarra = codigoBarra; }

    public String getTalla() { return talla; }
    public void setTalla(String talla) { this.talla = talla; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Integer getStockActual() { return stockActual; }
    public void setStockActual(Integer stockActual) { this.stockActual = stockActual; }

    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public boolean actualizarStock(int cantidad) { int nuevoStock=Math.addExact(stockActual,cantidad); if (nuevoStock<0) throw new IllegalArgumentException("Stock insuficiente para esta operación"); stockActual=nuevoStock; return stockActual<=stockMinimo; }
    public boolean necesitaReabastecimiento() { return stockActual<=stockMinimo; }
}
