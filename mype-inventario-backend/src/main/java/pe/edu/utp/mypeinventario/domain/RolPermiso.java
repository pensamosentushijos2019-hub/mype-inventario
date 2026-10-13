package pe.edu.utp.mypeinventario.domain;

import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;

public class RolPermiso {
    private String id;
    private String nombre;
    private List<String> permisos = new ArrayList<>();

    @com.fasterxml.jackson.annotation.JsonCreator
    public RolPermiso() {
        this.id = UUID.randomUUID().toString();
    }

    public RolPermiso(String nombre, List<String> permisos) {
        this();
        this.nombre = nombre;
        this.permisos = permisos;
        if (this.nombre == null || this.nombre.isBlank()) throw new IllegalArgumentException("nombre es obligatorio");
    }

    public String getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<String> getPermisos() { return permisos; }
    public void setPermisos(List<String> permisos) { this.permisos = permisos; }

    public boolean tienePermiso(String permiso) { return permisos.contains(permiso); }
    public void agregarPermiso(String permiso) { if (!permisos.contains(permiso)) permisos.add(permiso); }
}
