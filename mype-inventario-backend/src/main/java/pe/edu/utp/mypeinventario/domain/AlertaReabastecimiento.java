package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class AlertaReabastecimiento {
    private String id;
    private String varianteId;
    private String comercianteId;
    private String mensaje;
    private String estado = "pendiente";
    private String fechaGeneracion;

    @com.fasterxml.jackson.annotation.JsonCreator
    public AlertaReabastecimiento() {
        this.id = UUID.randomUUID().toString();
        this.fechaGeneracion = Instant.now().toString();
    }

    public AlertaReabastecimiento(String varianteId, String comercianteId, String mensaje, String estado, String fechaGeneracion) {
        this();
        this.varianteId = varianteId;
        this.comercianteId = comercianteId;
        this.mensaje = mensaje;
        this.estado = estado;
        this.fechaGeneracion = fechaGeneracion;
        if (this.varianteId == null || this.varianteId.isBlank()) throw new IllegalArgumentException("varianteId es obligatorio");
        if (this.comercianteId == null || this.comercianteId.isBlank()) throw new IllegalArgumentException("comercianteId es obligatorio");
        this.mensaje = (mensaje == null || mensaje.isBlank()) ? "El stock de este producto está por agotarse." : mensaje;
        this.fechaGeneracion = Instant.now().toString();
    }

    // Constructor de conveniencia usado por MovimientoController y VentaController
    // al generar una alerta automática (estado y fechaGeneracion toman su valor por defecto).
    public AlertaReabastecimiento(String varianteId, String comercianteId, String mensaje) {
        this(varianteId, comercianteId, mensaje, "pendiente", null);
    }

    public String getId() { return id; }

    public String getVarianteId() { return varianteId; }
    public void setVarianteId(String varianteId) { this.varianteId = varianteId; }

    public String getComercianteId() { return comercianteId; }
    public void setComercianteId(String comercianteId) { this.comercianteId = comercianteId; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaGeneracion() { return fechaGeneracion; }

    public void notificar() { if (!"resuelta".equals(this.estado)) this.estado = "notificada"; }
    public void resolver() { this.estado = "resuelta"; }
}
