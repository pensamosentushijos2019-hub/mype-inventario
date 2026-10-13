package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class VentaPunto {
    private String id;
    private String comercianteId;
    private String metodoPago;
    private String fecha;
    private List<Map<String,Object>> detalle = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    @com.fasterxml.jackson.annotation.JsonCreator
    public VentaPunto() {
        this.id = UUID.randomUUID().toString();
        this.fecha = Instant.now().toString();
    }

    public VentaPunto(String comercianteId, String metodoPago, String fecha, List<Map<String,Object>> detalle, BigDecimal total) {
        this();
        this.comercianteId = comercianteId;
        this.metodoPago = metodoPago;
        this.fecha = fecha;
        this.detalle = detalle;
        this.total = total;
        if (this.comercianteId == null || this.comercianteId.isBlank()) throw new IllegalArgumentException("comercianteId es obligatorio");
        if (!Set.of("efectivo","yape","plin","tarjeta").contains(this.metodoPago)) throw new IllegalArgumentException("metodoPago inválido. Debe ser uno de: efectivo, yape, plin, tarjeta");
        this.fecha=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getComercianteId() { return comercianteId; }
    public void setComercianteId(String comercianteId) { this.comercianteId = comercianteId; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getFecha() { return fecha; }

    public List<Map<String,Object>> getDetalle() { return detalle; }
    public void setDetalle(List<Map<String,Object>> detalle) { this.detalle = detalle; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public void registrarVenta(String varianteId, int cantidad, BigDecimal precioUnitario) { if (cantidad<=0) throw new IllegalArgumentException("cantidad debe ser mayor a 0"); Map<String,Object> item=new LinkedHashMap<>(); item.put("varianteId",varianteId); item.put("cantidad",cantidad); item.put("precioUnitario",precioUnitario); detalle.add(item); total=total.add(precioUnitario.multiply(BigDecimal.valueOf(cantidad))); }
    public Map<String,Object> generarComprobante() { Map<String,Object> c=new LinkedHashMap<>(); c.put("ventaId",id); c.put("fecha",fecha); c.put("items",detalle.size()); c.put("total",total); c.put("metodoPago",metodoPago); return c; }
}
