package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class Usuario {
    private String id;
    private String comercianteId;
    private String nombre;
    private String email;
    private String passwordHash;
    private String rolId;
    private Boolean activo = true;
    private String fechaCreacion;

    @com.fasterxml.jackson.annotation.JsonCreator
    public Usuario() {
        this.id = UUID.randomUUID().toString();
        this.fechaCreacion = Instant.now().toString();
    }

    public Usuario(String comercianteId, String nombre, String email, String passwordHash, String rolId, Boolean activo, String fechaCreacion) {
        this();
        this.comercianteId = comercianteId;
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rolId = rolId;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
        if (this.comercianteId == null || this.comercianteId.isBlank()) throw new IllegalArgumentException("comercianteId es obligatorio");
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
        if (this.email == null || this.email.isBlank()) throw new IllegalArgumentException("email es obligatorio");
        if (this.passwordHash == null || this.passwordHash.isBlank()) throw new IllegalArgumentException("passwordHash es obligatorio");
        this.fechaCreacion=Instant.now().toString();
    }

    public String getId() { return id; }

    public String getComercianteId() { return comercianteId; }
    public void setComercianteId(String comercianteId) { this.comercianteId = comercianteId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRolId() { return rolId; }
    public void setRolId(String rolId) { this.rolId = rolId; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public String getFechaCreacion() { return fechaCreacion; }

    public void cambiarPassword(String nuevoPasswordHash) { if (nuevoPasswordHash == null || nuevoPasswordHash.isBlank()) throw new IllegalArgumentException("nuevoPasswordHash es obligatorio"); this.passwordHash=nuevoPasswordHash; }
    public void desactivar() { this.activo=false; }
}
