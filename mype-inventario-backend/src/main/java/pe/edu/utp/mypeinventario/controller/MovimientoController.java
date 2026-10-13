package pe.edu.utp.mypeinventario.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.mypeinventario.domain.*;
import pe.edu.utp.mypeinventario.dto.MovimientoRequest;
import pe.edu.utp.mypeinventario.repository.Repositories;
import pe.edu.utp.mypeinventario.service.InventarioFacade;
import java.util.*;
@RestController @RequestMapping("/api/movimientos")
public class MovimientoController {
 private final Repositories repos; private final InventarioFacade facade;
 public MovimientoController(Repositories r,InventarioFacade f) {repos=r;facade=f;}
 @PostMapping public ResponseEntity<?> crear(@RequestBody MovimientoRequest req) {return ResponseEntity.status(201).body(facade.registrarMovimiento(req));}
 @GetMapping public List<MovimientoInventario> todos() {return repos.movimientos.getAll();}
 @GetMapping("/{varianteId}") public List<MovimientoInventario> historial(@PathVariable String varianteId) {return repos.movimientos.getAll().stream().filter(m->varianteId.equals(m.getVarianteId())).toList();}
}
