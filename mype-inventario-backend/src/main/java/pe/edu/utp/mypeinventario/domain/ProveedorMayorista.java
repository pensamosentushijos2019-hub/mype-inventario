package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class ProveedorMayorista {
    private String id;
    private String razonSocial;
    private String categoriaAtendida;
    private String contacto;

    @com.fasterxml.jackson.annotation.JsonCreator
    public ProveedorMayorista() {
        this.id = UUID.randomUUID().toString();
    }

    public ProveedorMayorista(String razonSocial, String categoriaAtendida, String contacto) {
        this();
        this.razonSocial = razonSocial;
        this.categoriaAtendida = categoriaAtendida;
        this.contacto = contacto;
        if (this.razonSocial == null || this.razonSocial.isBlank()) throw new IllegalArgumentException("razonSocial es obligatorio");
    }

    public String getId() { return id; }

    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }

    public String getCategoriaAtendida() { return categoriaAtendida; }
    public void setCategoriaAtendida(String categoriaAtendida) { this.categoriaAtendida = categoriaAtendida; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public int consultarDemandaCategoria(Map<String,Integer> ventasPorCategoria) { return ventasPorCategoria.getOrDefault(categoriaAtendida, 0); }
}
