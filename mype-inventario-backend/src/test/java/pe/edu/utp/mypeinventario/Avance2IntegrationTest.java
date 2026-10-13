package pe.edu.utp.mypeinventario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import pe.edu.utp.mypeinventario.domain.*;
import pe.edu.utp.mypeinventario.dto.*;
import pe.edu.utp.mypeinventario.repository.Repositories;
import pe.edu.utp.mypeinventario.service.InventarioFacade;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:avance2test;DB_CLOSE_DELAY=-1"})
class Avance2IntegrationTest {
 @Autowired InventarioFacade facade;
 @Autowired Repositories repos;
 @Autowired JdbcTemplate jdbc;
 @Autowired com.fasterxml.jackson.databind.ObjectMapper json;
 Comerciante comerciante;
 VarianteSKU sku;
 @BeforeEach void preparar() {
  for(String table:List.of("detalleventa","alertareabastecimiento","movimientoinventario","ventapunto","variantesku","productoitem","comerciante")) jdbc.update("DELETE FROM "+table);
  comerciante=new Comerciante();comerciante.setNombreNegocio("Prueba");comerciante.setTelefono("999111222");repos.comerciantes.add(comerciante.getId(),comerciante);
  var p=new ProductoItem();p.setNombre("Polo");p.setComercianteId(comerciante.getId());p.setPrecioBase(new BigDecimal("39.90"));facade.crearProducto(p);
  sku=facade.agregarVariante(p.getId(),new VarianteSKU(p.getId(),"SKU-TEST","M","Azul",10,5,new BigDecimal("39.90")));
 }
 VentaRequest venta(List<VentaRequest.Item> items) {return new VentaRequest(comerciante.getId(),"yape",items);}
 @Test void ventaPersisteTotalStockKardexYDetalle() {
  var result=facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),2))));
  var v=(VentaPunto)result.get("venta");assertEquals(0,v.getTotal().compareTo(new BigDecimal("79.80")));
  assertEquals(8,repos.variantes.getById(sku.getId()).getStockActual());assertEquals(1,repos.movimientos.getAll().size());assertEquals(1,repos.detallesVenta.getAll().size());assertEquals(v.getId(),repos.ventas.getById(v.getId()).getId());
 }
 @Test void ventaInvalidaNoDescuentaPrimerSku() {
  assertThrows(IllegalArgumentException.class,()->facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),2),new VentaRequest.Item("inexistente",1)))));
  assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());assertTrue(repos.ventas.getAll().isEmpty());assertTrue(repos.movimientos.getAll().isEmpty());
 }
 @Test void skuRepetidoSeAgrupaAntesDeValidar() {
  assertThrows(IllegalArgumentException.class,()->facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),6),new VentaRequest.Item(sku.getId(),6)))));
  assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());
  var result=facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),2),new VentaRequest.Item(sku.getId(),3))));
  assertEquals(1,((VentaPunto)result.get("venta")).getDetalle().size());assertEquals(5,repos.variantes.getById(sku.getId()).getStockActual());
 }
 @Test void ventaVaciaYMetodoNuloSeRechazan() {
  assertThrows(IllegalArgumentException.class,()->facade.registrarVenta(venta(List.of())));
  assertThrows(IllegalArgumentException.class,()->facade.registrarVenta(new VentaRequest(comerciante.getId(),null,List.of(new VentaRequest.Item(sku.getId(),1)))));
 }
 @Test void cantidadesInvalidasNoAlteranStock() {
  assertThrows(IllegalArgumentException.class,()->facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),0)))));
  assertThrows(IllegalArgumentException.class,()->facade.registrarMovimiento(new MovimientoRequest(sku.getId(),"entrada",null,"",comerciante.getId())));
  assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());
 }
 @Test void alertaSeActualizaSinDuplicarseYSeResuelveAlReponer() {
  facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),5))));
  facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),1))));
  assertEquals(1,repos.alertas.getAll().size());assertTrue(repos.alertas.getAll().get(0).getMensaje().contains("4 unidades"));
  facade.registrarMovimiento(new MovimientoRequest(sku.getId(),"entrada",10,"Reposición",comerciante.getId()));
  assertEquals(14,repos.variantes.getById(sku.getId()).getStockActual());assertEquals("resuelta",repos.alertas.getAll().get(0).getEstado());
 }
 @Test void codigoBarrasUnicoYRelacionPersistida() {
  assertThrows(DataIntegrityViolationException.class,()->facade.agregarVariante(sku.getProductoId(),new VarianteSKU(sku.getProductoId(),"SKU-TEST","L","Rojo",1,0,BigDecimal.ONE)));
  assertEquals(1,repos.variantes.getAll().size());assertEquals(List.of(sku.getId()),repos.productos.getById(sku.getProductoId()).getVariantes());
 }
 @Test void falloSqlRevierteStockYMovimiento() {
  jdbc.execute("ALTER TABLE movimientoinventario ADD CONSTRAINT reject_test CHECK (LENGTH(payload)<5)");
  try {
   assertThrows(DataIntegrityViolationException.class,()->facade.registrarMovimiento(new MovimientoRequest(sku.getId(),"salida",2,"Ajuste",comerciante.getId())));
   assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());assertTrue(repos.movimientos.getAll().isEmpty());
  } finally {jdbc.execute("ALTER TABLE movimientoinventario DROP CONSTRAINT reject_test");}
 }
 @Test void ventasConcurrentesNoSobrevenden() throws Exception {
  var start=new CountDownLatch(1);var pool=Executors.newFixedThreadPool(2);
  Callable<Boolean> vender=()->{start.await();try{facade.registrarVenta(venta(List.of(new VentaRequest.Item(sku.getId(),7))));return true;}catch(IllegalArgumentException e){return false;}};
  try{var a=pool.submit(vender);var b=pool.submit(vender);start.countDown();int exitos=(a.get(10,TimeUnit.SECONDS)?1:0)+(b.get(10,TimeUnit.SECONDS)?1:0);assertEquals(1,exitos);assertEquals(3,repos.variantes.getById(sku.getId()).getStockActual());assertEquals(1,repos.ventas.getAll().size());}finally{pool.shutdownNow();}
 }
 @Test void skuAjenoSeRechaza() {
  var otro=new Comerciante();otro.setNombreNegocio("Otra tienda");otro.setTelefono("123");repos.comerciantes.add(otro.getId(),otro);
  assertThrows(IllegalArgumentException.class,()->facade.registrarVenta(new VentaRequest(otro.getId(),"efectivo",List.of(new VentaRequest.Item(sku.getId(),1)))));
  assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());
 }
 @Test void daoRecuperaIdentidadFechaYDatosSinUsarObjetoEnMemoria() {
  var loaded=repos.variantes.getById(sku.getId());assertNotSame(sku,loaded);assertEquals(sku.getId(),loaded.getId());loaded.setStockActual(99);assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());
  assertEquals(comerciante.getFechaRegistro(),repos.comerciantes.getById(comerciante.getId()).getFechaRegistro());
 }
 @Test void jacksonHttpPermiteSkuSinProductoIdHastaQueFacadeLoAsigna() throws Exception {
  var parsed=json.readValue("{\"codigoBarra\":\"SKU-HTTP\",\"stockActual\":2,\"stockMinimo\":1,\"precioVenta\":10.50}",VarianteSKU.class);
  assertNull(parsed.getProductoId());assertNotNull(parsed.getId());
  var saved=facade.agregarVariante(sku.getProductoId(),parsed);
  assertEquals(sku.getProductoId(),saved.getProductoId());
 }
 @Test void overflowNoPermiteStockNegativo() {
  assertThrows(ArithmeticException.class,()->facade.registrarMovimiento(new MovimientoRequest(sku.getId(),"entrada",Integer.MAX_VALUE,"",comerciante.getId())));
  assertEquals(10,repos.variantes.getById(sku.getId()).getStockActual());
 }
}
