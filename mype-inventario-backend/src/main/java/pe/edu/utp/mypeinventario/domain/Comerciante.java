package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Comerciante {
    private String id;
    private String nombreNegocio;
    private String rubro;
    private String telefono;
    private String planSuscripcion = "trial";
    private String fechaRegistro;
    private List<String> productos = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonCreator
    public Comerciante() {
        this.id = UUID.randomUUID().toString();
        this.fechaRegistro = Instant.now().toString();
    }

    public Comerciante(String nombreNegocio, String rubro, String telefono, String planSuscripcion, String fechaRegistro, List<String> productos) {
        this();
        this.nombreNegocio = nombreNegocio;
        this.rubro = rubro;
        this.telefono = telefono;
        this.planSuscripcion = planSuscripcion;
        this.fechaRegistro = fechaRegistro;
        this.productos = productos;
        if (this.nombreNegocio == null || this.nombreNegocio.isBlank()) throw new IllegalArgumentException("nombreNegocio es obligatorio");
        if (this.telefono == null || this.telefono.isBlank()) throw new IllegalArgumentException("telefono es obligatorio");
        this.fechaRegistro = Instant.now().toString();
    }

    public String getId() { return id; }

    public String getNombreNegocio() { return nombreNegocio; }
    public void setNombreNegocio(String nombreNegocio) { this.nombreNegocio = nombreNegocio; }

    public String getRubro() { return rubro; }
    public void setRubro(String rubro) { this.rubro = rubro; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getPlanSuscripcion() { return planSuscripcion; }
    public void setPlanSuscripcion(String planSuscripcion) { this.planSuscripcion = planSuscripcion; }

    public String getFechaRegistro() { return fechaRegistro; }

    public List<String> getProductos() { return productos; }
    public void setProductos(List<String> productos) { this.productos = productos; }

    public void registrarProducto(String productoId) { productos.add(productoId); }
    public void cambiarPlan(String nuevoPlan) { if (!Set.of("trial","basico","pro").contains(nuevoPlan)) throw new IllegalArgumentException("Plan inválido. Debe ser uno de: trial, basico, pro"); this.planSuscripcion=nuevoPlan; }
}
