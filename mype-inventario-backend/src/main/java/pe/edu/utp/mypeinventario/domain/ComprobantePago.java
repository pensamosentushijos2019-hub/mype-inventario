package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class ComprobantePago {
    private String id;
    private String ventaId;
    private String tipo = "boleta";
    private String serie;
    private String numero;
    private String fechaEmision;

    @com.fasterxml.jackson.annotation.JsonCreator
    public ComprobantePago() {
        this.id = UUID.randomUUID().toString();
        this.fechaEmision = Instant.now().toString();
    }

    public ComprobantePago(String ventaId, String tipo, String serie, String numero, String fechaEmision) {
        this();
        this.ventaId = ventaId;
        this.tipo = tipo;
        this.serie = serie;
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        if (this.ventaId == null || this.ventaId.isBlank()) throw new IllegalArgumentException("ventaId es obligatorio");
        if (!Set.of("boleta","factura").contains(this.tipo)) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: boleta, factura");
        if (this.serie == null || this.serie.isBlank()) throw new IllegalArgumentException("serie es obligatoria");
        if (this.numero == null || this.numero.isBlank()) throw new IllegalArgumentException("numero es obligatorio");
        this.fechaEmision=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getVentaId() { return ventaId; }
    public void setVentaId(String ventaId) { this.ventaId = ventaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getFechaEmision() { return fechaEmision; }

    public String generarNumeroCompleto() { return serie + "-" + numero; }
}
