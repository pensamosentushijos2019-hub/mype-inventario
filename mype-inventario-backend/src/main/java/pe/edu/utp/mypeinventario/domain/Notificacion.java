package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Notificacion {
    private String id;
    private String comercianteId;
    private String tipo;
    private String mensaje;
    private Boolean leida = false;
    private String fechaEnvio;

    @com.fasterxml.jackson.annotation.JsonCreator
    public Notificacion() {
        this.id = UUID.randomUUID().toString();
        this.fechaEnvio = Instant.now().toString();
    }

    public Notificacion(String comercianteId, String tipo, String mensaje, Boolean leida, String fechaEnvio) {
        this();
        this.comercianteId = comercianteId;
        this.tipo = tipo;
        this.mensaje = mensaje;
        this.leida = leida;
        this.fechaEnvio = fechaEnvio;
        if (this.comercianteId == null || this.comercianteId.isBlank()) throw new IllegalArgumentException("comercianteId es obligatorio");
        if (!Set.of("alerta_stock","venta","sistema").contains(this.tipo)) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: alerta_stock, venta, sistema");
        if (this.mensaje == null || this.mensaje.isBlank()) throw new IllegalArgumentException("mensaje es obligatorio");
        this.fechaEnvio=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getComercianteId() { return comercianteId; }
    public void setComercianteId(String comercianteId) { this.comercianteId = comercianteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Boolean getLeida() { return leida; }
    public void setLeida(Boolean leida) { this.leida = leida; }

    public String getFechaEnvio() { return fechaEnvio; }

    public void marcarComoLeida() { this.leida=true; }
}
