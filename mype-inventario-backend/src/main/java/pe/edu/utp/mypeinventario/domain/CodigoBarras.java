package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class CodigoBarras {
    private String id;
    private String varianteId;
    private String codigo;
    private String tipo = "EAN13";

    @com.fasterxml.jackson.annotation.JsonCreator
    public CodigoBarras() {
        this.id = UUID.randomUUID().toString();
    }

    public CodigoBarras(String varianteId, String codigo, String tipo) {
        this();
        this.varianteId = varianteId;
        this.codigo = codigo;
        this.tipo = tipo;
        if (this.varianteId == null || this.varianteId.isBlank()) throw new IllegalArgumentException("varianteId es obligatorio");
        if (this.codigo == null || this.codigo.isBlank()) throw new IllegalArgumentException("codigo es obligatorio");
        if (!Set.of("EAN13","UPC","CODE128").contains(this.tipo)) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: EAN13, UPC, CODE128");
    }

    public String getId() { return id; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

}
