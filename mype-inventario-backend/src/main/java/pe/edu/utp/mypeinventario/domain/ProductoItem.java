package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class ProductoItem {
    private String id;
    private String comercianteId;
    private String proveedorId;
    private String nombre;
    private String categoria;
    private String descripcion = "";
    private BigDecimal precioBase;
    private List<String> variantes = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonCreator
    public ProductoItem() {
        this.id = UUID.randomUUID().toString();
    }

    public ProductoItem(String comercianteId, String proveedorId, String nombre, String categoria, String descripcion, BigDecimal precioBase, List<String> variantes) {
        this();
        this.comercianteId = comercianteId;
        this.proveedorId = proveedorId;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precioBase = precioBase;
        this.variantes = variantes;
        if (this.comercianteId == null || this.comercianteId.isBlank()) throw new IllegalArgumentException("comercianteId es obligatorio");
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
        if (this.precioBase == null || this.precioBase.compareTo(BigDecimal.ZERO)<0) throw new IllegalArgumentException("precioBase debe ser un número válido");
    }

    public String getId() { return id; }

    public String getComercianteId() { return comercianteId; }
    public void setComercianteId(String comercianteId) { this.comercianteId = comercianteId; }

    public String getProveedorId() { return proveedorId; }
    public void setProveedorId(String proveedorId) { this.proveedorId = proveedorId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }

    public List<String> getVariantes() { return variantes; }
    public void setVariantes(List<String> variantes) { this.variantes = variantes; }

    public void agregarVariante(String varianteId) { variantes.add(varianteId); }
}
