package pe.edu.utp.mypeinventario;

import org.junit.jupiter.api.Test;
import pe.edu.utp.mypeinventario.domain.*;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas funcionales sobre la lógica de dominio real (sin levantar el
 * contexto de Spring; se instancian y ejercitan directamente las clases
 * de dominio, igual que hacen los controladores).
 *
 * Ejecutar con: mvn test
 *
 */
class DomainFuncionalTest {

    @Test
    void rf01_registrarComercianteYUsuarioConRol() {
        Comerciante comerciante = new Comerciante();
        comerciante.setNombreNegocio("Boutique Ana");
        comerciante.setRubro("ropa");
        comerciante.setTelefono("999111222");

        RolPermiso rol = new RolPermiso();
        rol.setNombre("admin");
        rol.setPermisos(java.util.List.of("ventas.crear", "inventario.editar"));

        Usuario usuario = new Usuario();
        usuario.setComercianteId(comerciante.getId());
        usuario.setNombre("Ana Torres");
        usuario.setEmail("ana@boutique.pe");
        usuario.setPasswordHash("hash123");
        usuario.setRolId(rol.getId());

        assertNotNull(comerciante.getId());
        assertNotNull(usuario.getId());
        assertEquals(comerciante.getId(), usuario.getComercianteId());
    }

    @Test
    void rf02_rf03_registrarProductoYVariante() {
        Comerciante comerciante = new Comerciante();
        comerciante.setNombreNegocio("Boutique Ana");
        comerciante.setTelefono("999111222");

        ProductoItem producto = new ProductoItem();
        producto.setComercianteId(comerciante.getId());
        producto.setNombre("Polo básico algodón");
        producto.setCategoria("ropa");
        producto.setPrecioBase(new BigDecimal("35.00"));
        comerciante.registrarProducto(producto.getId());

        VarianteSKU variante = new VarianteSKU(
                producto.getId(), "7751234567895", "M", "azul",
                20, 5, new BigDecimal("39.90"));
        producto.agregarVariante(variante.getId());

        assertTrue(comerciante.getProductos().contains(producto.getId()));
        assertTrue(producto.getVariantes().contains(variante.getId()));
    }

    @Test
    void rf04_registrarMovimientoYActualizarStock() {
        VarianteSKU variante = new VarianteSKU(
                "prod-1", "7751234567895", "M", "azul",
                20, 5, new BigDecimal("39.90"));

        boolean necesitaAlerta = variante.actualizarStock(-3); // salida de 3
        assertEquals(17, variante.getStockActual());
        assertFalse(necesitaAlerta); // 17 > stockMinimo (5)
    }

    @Test
    void rf06_generarAlertaCuandoStockLlegaAlMinimo() {
        VarianteSKU variante = new VarianteSKU(
                "prod-1", "7751234567895", "M", "azul",
                17, 5, new BigDecimal("39.90"));

        boolean necesitaAlerta = variante.actualizarStock(-13); // 17 -> 4
        assertTrue(necesitaAlerta);

        AlertaReabastecimiento alerta = new AlertaReabastecimiento();
        alerta.setVarianteId(variante.getId());
        alerta.setComercianteId("comerciante-1");
        alerta.setMensaje("Stock bajo: quedan 4 unidades.");
        alerta.notificar();
        assertEquals("notificada", alerta.getEstado());
    }

    @Test
    void rf05_registrarVentaYGenerarComprobante() {
        VarianteSKU variante = new VarianteSKU(
                "prod-1", "7751234567895", "M", "azul",
                20, 5, new BigDecimal("39.90"));

        VentaPunto venta = new VentaPunto();
        venta.setComercianteId("comerciante-1");
        venta.setMetodoPago("yape");
        venta.registrarVenta(variante.getId(), 2, variante.getPrecioVenta());

        Object comprobante = venta.generarComprobante();
        assertEquals(0, venta.getTotal().compareTo(new BigDecimal("79.80")));
        assertNotNull(comprobante);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setVentaId(venta.getId());
        detalle.setVarianteId(variante.getId());
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(variante.getPrecioVenta());
        assertEquals(0, detalle.calcularSubtotal().compareTo(new BigDecimal("79.80")));
    }

    @Test
    void extra_aplicarDescuentoVigenteSobrePrecio() {
        String hoy = Instant.now().toString();
        String manana = Instant.now().plusSeconds(86400).toString();

        DescuentoPromocion promo = new DescuentoPromocion();
        promo.setVarianteId("var-1");
        promo.setPorcentaje(new BigDecimal("20"));
        promo.setFechaInicio(hoy);
        promo.setFechaFin(manana);

        assertTrue(promo.estaActivo(hoy));
        BigDecimal conDescuento = promo.aplicarA(new BigDecimal("39.90"));
        assertEquals(0, conDescuento.compareTo(new BigDecimal("31.92")));
    }

    @Test
    void rf10_proveedorConsultaDemandaPorCategoria() {
        ProveedorMayorista proveedor = new ProveedorMayorista();
        proveedor.setRazonSocial("Distribuidora Lima SAC");
        proveedor.setCategoriaAtendida("ropa");

        java.util.Map<String, Integer> demandaPorCategoria = java.util.Map.of("ropa", 480, "calzado", 120);
        Integer demanda = proveedor.consultarDemandaCategoria(demandaPorCategoria);
        assertEquals(480, demanda);
    }

    @Test
    void validacion_noPermiteStockNegativo() {
        VarianteSKU variante = new VarianteSKU(
                "prod-1", "7751234567895", "M", "azul",
                5, 5, new BigDecimal("39.90"));
        assertThrows(IllegalArgumentException.class, () -> variante.actualizarStock(-999));
    }

    @Test
    void validacion_productoItemExigeComercianteNombreYPrecioValido() {
        assertThrows(IllegalArgumentException.class, () -> {
            ProductoItem p = new ProductoItem();
            p.setComercianteId(null);
            p.setNombre("X");
            p.setPrecioBase(new BigDecimal("10"));
            pe.edu.utp.mypeinventario.config.DomainValidation.validate(p);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            ProductoItem p = new ProductoItem();
            p.setComercianteId("c1");
            p.setNombre("X");
            p.setPrecioBase(new BigDecimal("-5"));
            pe.edu.utp.mypeinventario.config.DomainValidation.validate(p);
        });
    }
}
