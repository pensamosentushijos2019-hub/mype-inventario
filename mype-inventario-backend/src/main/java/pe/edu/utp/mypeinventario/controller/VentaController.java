package pe.edu.utp.mypeinventario.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.mypeinventario.domain.*;
import pe.edu.utp.mypeinventario.dto.VentaRequest;
import pe.edu.utp.mypeinventario.repository.Repositories;
import pe.edu.utp.mypeinventario.service.InventarioFacade;
import java.util.*;
@RestController @RequestMapping("/api/ventas")
public class VentaController {
 private final Repositories repos; private final InventarioFacade facade;
 public VentaController(Repositories r,InventarioFacade f) {repos=r;facade=f;}
 @PostMapping public ResponseEntity<?> crear(@RequestBody VentaRequest req) {return ResponseEntity.status(201).body(facade.registrarVenta(req));}
 @GetMapping public List<VentaPunto> listar() {return repos.ventas.getAll();}
 @GetMapping("/alertas/{comercianteId}") public List<AlertaReabastecimiento> alertas(@PathVariable String comercianteId) {return repos.alertas.getAll().stream().filter(a->comercianteId.equals(a.getComercianteId())).toList();}
}
