package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class EtiquetaProducto {
    private String id;
    private String productoId;
    private String texto;

    @com.fasterxml.jackson.annotation.JsonCreator
    public EtiquetaProducto() {
        this.id = UUID.randomUUID().toString();
    }

    public EtiquetaProducto(String productoId, String texto) {
        this();
        this.productoId = productoId;
        this.texto = texto;
        if (this.productoId == null || this.productoId.isBlank()) throw new IllegalArgumentException("productoId es obligatorio");
        if (this.texto == null || this.texto.isBlank()) throw new IllegalArgumentException("texto es obligatorio");
        this.texto=this.texto.toLowerCase().trim();
    }

    public String getId() { return id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto == null ? null : texto.toLowerCase().trim(); }

}
