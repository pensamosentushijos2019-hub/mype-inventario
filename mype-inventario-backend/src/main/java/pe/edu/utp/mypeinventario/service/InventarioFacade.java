package pe.edu.utp.mypeinventario.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.mypeinventario.repository.Repositories;
import pe.edu.utp.mypeinventario.domain.*;
import pe.edu.utp.mypeinventario.dto.*;
import pe.edu.utp.mypeinventario.config.DomainValidation;
import java.math.BigDecimal;
import java.util.*;
@Service
public class InventarioFacade {
 private final Repositories repos;
 public InventarioFacade(Repositories repos) { this.repos=repos; }
 private void comercio(String id) {
  if(id==null || repos.comerciantes.getById(id)==null) throw new IllegalArgumentException("Comerciante no encontrado");
 }
 private ProductoItem productoDe(VarianteSKU v,String comerciante) {
  if(v==null) throw new IllegalArgumentException("VarianteSKU no encontrada");
  var p=repos.productos.getById(v.getProductoId());
  if(p==null || !p.getComercianteId().equals(comerciante)) throw new IllegalArgumentException("El SKU no pertenece al comerciante");
  return p;
 }
 @Transactional
 public ProductoItem crearProducto(ProductoItem p) {
  DomainValidation.validate(p); comercio(p.getComercianteId());
  var c=repos.comerciantes.lock(p.getComercianteId());
  // Relaciones manejadas por el servidor.
  p.setVariantes(new ArrayList<>()); repos.productos.add(p.getId(),p);
  c.registrarProducto(p.getId()); repos.comerciantes.add(c.getId(),c); return p;
 }
 @Transactional
 public VarianteSKU agregarVariante(String id,VarianteSKU v) {
  var p=repos.productos.lock(id); if(p==null) throw new IllegalArgumentException("Producto no encontrado");
  v.setProductoId(id); DomainValidation.validate(v);
  if(v.getPrecioVenta().scale()>2) throw new IllegalArgumentException("El precio admite hasta 2 decimales");
  repos.variantes.add(v.getId(),v); p.agregarVariante(v.getId()); repos.productos.add(id,p);
  if(v.necesitaReabastecimiento()) alerta(v,p.getComercianteId());
  return v;
 }
 @Transactional
 public ProductoItem editarProducto(String id,ProductoItem cambios) {
  var p=repos.productos.lock(id); if(p==null) throw new IllegalArgumentException("Producto no encontrado");
  p.setNombre(cambios.getNombre()); p.setCategoria(cambios.getCategoria()); p.setDescripcion(cambios.getDescripcion()); p.setPrecioBase(cambios.getPrecioBase());
  DomainValidation.validate(p); return repos.productos.add(id,p);
 }
 private AlertaReabastecimiento alerta(VarianteSKU v,String comercio) {
  var existentes=repos.alertas.getAll().stream().filter(a->a.getVarianteId().equals(v.getId()) && !"resuelta".equals(a.getEstado())).toList();
  if(!v.necesitaReabastecimiento()) {
   for(var a:existentes) { a.resolver(); repos.alertas.add(a.getId(),a); }
   return null;
  }
  var a=existentes.isEmpty()?new AlertaReabastecimiento(v.getId(),comercio,""):existentes.get(0);
  a.setMensaje("Stock bajo para SKU "+v.getCodigoBarra()+": quedan "+v.getStockActual()+" unidades.");
  a.notificar(); repos.alertas.add(a.getId(),a); return a;
 }
 @Transactional
 public Map<String,Object> registrarMovimiento(MovimientoRequest req) {
  comercio(req.comercianteId());
  var m=new MovimientoInventario(); m.setVarianteId(req.varianteId()); m.setTipo(req.tipo()); m.setCantidad(req.cantidad()); m.setMotivo(req.motivo()==null?"":req.motivo()); DomainValidation.validate(m);
  var v=repos.variantes.lock(req.varianteId()); productoDe(v,req.comercianteId());
  v.actualizarStock(m.cantidadConSigno()); repos.variantes.add(v.getId(),v); repos.movimientos.add(m.getId(),m);
  var a=alerta(v,req.comercianteId()); var result=new LinkedHashMap<String,Object>(); result.put("movimiento",m); result.put("varianteActualizada",v); result.put("alertaGenerada",a); return result;
 }
 @Transactional
 public Map<String,Object> registrarVenta(VentaRequest req) {
  comercio(req.comercianteId());
  if(req.metodoPago()==null || !Set.of("efectivo","yape","plin","tarjeta").contains(req.metodoPago())) throw new IllegalArgumentException("Método de pago inválido");
  if(req.items()==null || req.items().isEmpty()) throw new IllegalArgumentException("La venta necesita al menos un producto");
  var cantidades=new TreeMap<String,Integer>();
  for(var item:req.items()) {
   if(item==null || item.varianteId()==null || item.varianteId().isBlank() || item.cantidad()==null || item.cantidad()<=0) throw new IllegalArgumentException("Producto y cantidad positiva son obligatorios");
   cantidades.merge(item.varianteId(),item.cantidad(),Math::addExact);
  }
  // Bloqueos en orden estable evitan sobreventa y reducen interbloqueos.
  var variantes=new LinkedHashMap<String,VarianteSKU>();
  for(var entry:cantidades.entrySet()) {
   var v=repos.variantes.lock(entry.getKey()); productoDe(v,req.comercianteId());
   if(v.getStockActual()<entry.getValue()) throw new IllegalArgumentException("Stock insuficiente para SKU "+v.getCodigoBarra());
   variantes.put(v.getId(),v);
  }
  var venta=new VentaPunto(); venta.setComercianteId(req.comercianteId()); venta.setMetodoPago(req.metodoPago());
  for(var v:variantes.values()) venta.registrarVenta(v.getId(),cantidades.get(v.getId()),v.getPrecioVenta());
  repos.ventas.add(venta.getId(),venta);
  var alertas=new ArrayList<AlertaReabastecimiento>();
  for(var v:variantes.values()) {
   int cantidad=cantidades.get(v.getId()); v.actualizarStock(-cantidad); repos.variantes.add(v.getId(),v);
   var m=new MovimientoInventario(); m.setVarianteId(v.getId()); m.setTipo("salida"); m.setCantidad(cantidad); m.setMotivo("Venta "+venta.getId()); repos.movimientos.add(m.getId(),m);
   var detalle=new DetalleVenta(); detalle.setVentaId(venta.getId()); detalle.setVarianteId(v.getId()); detalle.setCantidad(cantidad); detalle.setPrecioUnitario(v.getPrecioVenta()); repos.detallesVenta.add(detalle.getId(),detalle);
   var a=alerta(v,req.comercianteId()); if(a!=null) alertas.add(a);
  }
  return Map.of("venta",venta,"comprobante",venta.generarComprobante(),"alertasGeneradas",alertas);
 }
}
