package pe.edu.utp.mypeinventario.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.mypeinventario.domain.*;
import pe.edu.utp.mypeinventario.repository.Repositories;
import pe.edu.utp.mypeinventario.service.InventarioFacade;
import java.util.*;
@RestController @RequestMapping("/api/productos")
public class ProductoController {
 private final Repositories repos; private final InventarioFacade facade;
 public ProductoController(Repositories r,InventarioFacade f) {repos=r;facade=f;}
 @PostMapping public ResponseEntity<?> crear(@RequestBody ProductoItem p) { return ResponseEntity.status(201).body(facade.crearProducto(p)); }
 @GetMapping public List<ProductoItem> listar() {return repos.productos.getAll();}
 @PutMapping("/{id}") public ProductoItem editar(@PathVariable String id,@RequestBody ProductoItem p) {return facade.editarProducto(id,p);}
 @GetMapping("/{id}") public ResponseEntity<?> obtener(@PathVariable String id) {var p=repos.productos.getById(id); return p==null?ResponseEntity.notFound().build():ResponseEntity.ok(p);}
 @PostMapping("/{id}/variantes") public ResponseEntity<?> agregar(@PathVariable String id,@RequestBody VarianteSKU v) {return ResponseEntity.status(201).body(facade.agregarVariante(id,v));}
 @GetMapping("/variantes") public List<VarianteSKU> variantes() {return repos.variantes.getAll();}
 @GetMapping("/variantes/bajo-stock") public List<VarianteSKU> bajoStock() {return repos.variantes.getAll().stream().filter(VarianteSKU::necesitaReabastecimiento).toList();}
}
