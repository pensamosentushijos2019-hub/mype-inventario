package pe.edu.utp.mypeinventario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.mypeinventario.config.DomainValidation;
import pe.edu.utp.mypeinventario.repository.Repository;
import java.util.List;

public abstract class CrudController<T> {
    protected final Repository<T> repo;
    protected final String nombreEntidad;

    protected CrudController(Repository<T> repo, String nombreEntidad) {
        this.repo = repo; this.nombreEntidad = nombreEntidad;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody T body) {
        try {
            DomainValidation.validate(body);
            // The request is already deserialized by Jackson. IDs/timestamps/defaults are initialized by the no-arg constructor.
            String id = (String) body.getClass().getMethod("getId").invoke(body);
            repo.add(id, body);
            return ResponseEntity.status(201).body(body);
        } catch (Exception e) {
            Throwable c=e.getCause(); String msg=(c instanceof IllegalArgumentException) ? c.getMessage() : e.getMessage();
            return ResponseEntity.badRequest().body(java.util.Map.of("error", msg == null ? "Solicitud inválida" : msg));
        }
    }

    @GetMapping
    public List<T> listar() { return repo.getAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable String id) {
        T entidad=repo.getById(id);
        if (entidad==null) return ResponseEntity.status(404).body(java.util.Map.of("error", nombreEntidad+" no encontrado"));
        return ResponseEntity.ok(entidad);
    }
}
