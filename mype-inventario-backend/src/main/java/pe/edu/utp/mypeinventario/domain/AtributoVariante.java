package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class AtributoVariante {
    private String id;
    private String varianteId;
    private String nombre;
    private String valor;

    @com.fasterxml.jackson.annotation.JsonCreator
    public AtributoVariante() {
        this.id = UUID.randomUUID().toString();
    }

    public AtributoVariante(String varianteId, String nombre, String valor) {
        this();
        this.varianteId = varianteId;
        this.nombre = nombre;
        this.valor = valor;
        require(this.varianteId,"varianteId es obligatorio"); require(this.nombre,"nombre es obligatorio"); require(this.valor,"valor es obligatorio");
    }

    public String getId() { return id; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    private static void require(String v, String msg) { if (v == null || v.isBlank()) throw new IllegalArgumentException(msg); }
}
