package pe.edu.utp.mypeinventario.config;

import pe.edu.utp.mypeinventario.domain.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

public final class DomainValidation {
    private DomainValidation() {}
    private static void req(String v, String msg) { if (v == null || v.isBlank()) throw new IllegalArgumentException(msg); }
    private static void positive(Integer v, String msg) { if (v == null || v <= 0) throw new IllegalArgumentException(msg); }
    private static void nonNegative(BigDecimal v, String msg) { if (v == null || v.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException(msg); }

    public static void validate(Object e) {
        if (e instanceof Comerciante x) { req(x.getNombreNegocio(), "nombreNegocio es obligatorio"); req(x.getTelefono(), "telefono es obligatorio"); if (!Set.of("trial","basico","pro").contains(x.getPlanSuscripcion())) throw new IllegalArgumentException("Plan inválido. Debe ser uno de: trial, basico, pro"); }
        else if (e instanceof AlertaReabastecimiento x) { req(x.getVarianteId(),"varianteId es obligatorio"); req(x.getComercianteId(),"comercianteId es obligatorio"); }
        else if (e instanceof Almacen x) { req(x.getComercianteId(),"comercianteId es obligatorio"); req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof AtributoVariante x) { req(x.getVarianteId(),"varianteId es obligatorio"); req(x.getNombre(),"nombre es obligatorio"); req(x.getValor(),"valor es obligatorio"); }
        else if (e instanceof Categoria x) { req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof CodigoBarras x) { req(x.getVarianteId(),"varianteId es obligatorio"); req(x.getCodigo(),"codigo es obligatorio"); if (!Set.of("EAN13","UPC","CODE128").contains(x.getTipo())) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: EAN13, UPC, CODE128"); }
        else if (e instanceof ComprobantePago x) { req(x.getVentaId(),"ventaId es obligatorio"); req(x.getSerie(),"serie es obligatoria"); req(x.getNumero(),"numero es obligatorio"); if (!Set.of("boleta","factura").contains(x.getTipo())) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: boleta, factura"); }
        else if (e instanceof DescuentoPromocion x) { req(x.getVarianteId(),"varianteId es obligatorio"); if (x.getPorcentaje()==null || x.getPorcentaje().compareTo(BigDecimal.ZERO)<=0 || x.getPorcentaje().compareTo(BigDecimal.valueOf(100))>0) throw new IllegalArgumentException("porcentaje debe estar entre 0 y 100"); req(x.getFechaInicio(),"fechaInicio y fechaFin son obligatorias"); req(x.getFechaFin(),"fechaInicio y fechaFin son obligatorias"); Instant.parse(x.getFechaInicio()); Instant.parse(x.getFechaFin()); }
        else if (e instanceof DetalleVenta x) { req(x.getVentaId(),"ventaId es obligatorio"); req(x.getVarianteId(),"varianteId es obligatorio"); positive(x.getCantidad(),"cantidad debe ser mayor a 0"); nonNegative(x.getPrecioUnitario(),"precioUnitario debe ser un número válido"); }
        else if (e instanceof EtiquetaProducto x) { req(x.getProductoId(),"productoId es obligatorio"); req(x.getTexto(),"texto es obligatorio"); }
        else if (e instanceof ImagenProducto x) { req(x.getProductoId(),"productoId es obligatorio"); req(x.getUrl(),"url es obligatoria"); }
        else if (e instanceof ListaPrecio x) { req(x.getVarianteId(),"varianteId es obligatorio"); nonNegative(x.getPrecio(),"precio debe ser un número válido"); req(x.getVigenciaDesde(),"vigenciaDesde es obligatoria"); Instant.parse(x.getVigenciaDesde()); }
        else if (e instanceof Lote x) { req(x.getProveedorId(),"proveedorId es obligatorio"); positive(x.getCantidad(),"cantidad debe ser mayor a 0"); }
        else if (e instanceof Marca x) { req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof MetodoPago x) { req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof MovimientoInventario x) { req(x.getVarianteId(),"varianteId es obligatorio"); if (!Set.of("entrada","salida").contains(x.getTipo())) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: entrada, salida"); positive(x.getCantidad(),"cantidad debe ser mayor a 0"); }
        else if (e instanceof Notificacion x) { req(x.getComercianteId(),"comercianteId es obligatorio"); if (!Set.of("alerta_stock","venta","sistema").contains(x.getTipo())) throw new IllegalArgumentException("tipo inválido. Debe ser uno de: alerta_stock, venta, sistema"); req(x.getMensaje(),"mensaje es obligatorio"); }
        else if (e instanceof ProductoItem x) { req(x.getComercianteId(),"comercianteId es obligatorio"); req(x.getNombre(),"nombre es obligatorio"); nonNegative(x.getPrecioBase(),"precioBase debe ser un número válido"); }
        else if (e instanceof ProveedorMayorista x) { req(x.getRazonSocial(),"razonSocial es obligatorio"); }
        else if (e instanceof RolPermiso x) { req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof Sucursal x) { req(x.getComercianteId(),"comercianteId es obligatorio"); req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof TipoMovimiento x) { req(x.getNombre(),"nombre es obligatorio"); }
        else if (e instanceof Usuario x) { req(x.getComercianteId(),"comercianteId es obligatorio"); req(x.getNombre(),"nombre es obligatorio"); req(x.getEmail(),"email es obligatorio"); req(x.getPasswordHash(),"passwordHash es obligatorio"); }
        else if (e instanceof VarianteSKU x) { req(x.getProductoId(),"productoId es obligatorio"); req(x.getCodigoBarra(),"codigoBarra es obligatorio"); nonNegative(x.getPrecioVenta(),"precioVenta debe ser un número válido"); if (x.getStockActual()==null || x.getStockActual()<0) throw new IllegalArgumentException("stockActual debe ser mayor o igual a 0"); if (x.getStockMinimo()==null || x.getStockMinimo()<0) throw new IllegalArgumentException("stockMinimo debe ser mayor o igual a 0"); }
    }
}
