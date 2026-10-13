package pe.edu.utp.mypeinventario.controller;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.mypeinventario.repository.Repositories;
import java.util.*;
import java.math.BigDecimal;
@RestController @RequestMapping("/api/dashboard") public class DashboardController {
 private final Repositories repos;public DashboardController(Repositories r){repos=r;}
 @GetMapping public Map<String,Object> resumen() {
  var ventas=repos.ventas.getAll();var variantes=repos.variantes.getAll();
  return Map.of("productos",repos.productos.getAll().size(),"unidades",variantes.stream().mapToLong(v->v.getStockActual()).sum(),"bajoStock",variantes.stream().filter(v->v.necesitaReabastecimiento()).count(),"ventas",ventas.size(),"totalVentas",ventas.stream().map(v->v.getTotal()).reduce(BigDecimal.ZERO,BigDecimal::add));
 }
}
