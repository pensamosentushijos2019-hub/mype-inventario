package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class ListaPrecio {
    private String id;
    private String varianteId;
    private BigDecimal precio;
    private String vigenciaDesde;

    @com.fasterxml.jackson.annotation.JsonCreator
    public ListaPrecio() {
        this.id = UUID.randomUUID().toString();
        this.vigenciaDesde = Instant.now().toString();
    }

    public ListaPrecio(String varianteId, BigDecimal precio, String vigenciaDesde) {
        this();
        this.varianteId = varianteId;
        this.precio = precio;
        this.vigenciaDesde = vigenciaDesde;
        if (this.varianteId == null || this.varianteId.isBlank()) throw new IllegalArgumentException("varianteId es obligatorio");
        if (this.precio == null || this.precio.compareTo(BigDecimal.ZERO)<0) throw new IllegalArgumentException("precio debe ser un número válido");
        if (this.vigenciaDesde == null || this.vigenciaDesde.isBlank()) this.vigenciaDesde=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public String getVigenciaDesde() { return vigenciaDesde; }
    public void setVigenciaDesde(String vigenciaDesde) { this.vigenciaDesde = vigenciaDesde; }

    public boolean estaVigente(String fecha) { return !Instant.parse(fecha).isBefore(Instant.parse(vigenciaDesde)); }
}
