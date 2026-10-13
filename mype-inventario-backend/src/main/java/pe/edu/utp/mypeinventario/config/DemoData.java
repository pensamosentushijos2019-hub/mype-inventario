package pe.edu.utp.mypeinventario.config;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.utp.mypeinventario.repository.Repositories;
import pe.edu.utp.mypeinventario.domain.*;
import pe.edu.utp.mypeinventario.service.InventarioFacade;
import java.math.BigDecimal;
@Component public class DemoData implements ApplicationRunner {
 private final Repositories repos; private final InventarioFacade facade;
 public DemoData(Repositories r,InventarioFacade f) {repos=r;facade=f;}
 @Override @Transactional public void run(ApplicationArguments args) {
  if(!repos.comerciantes.getAll().isEmpty()) return;
  var c=new Comerciante(); c.setNombreNegocio("Boutique Lima"); c.setRubro("Ropa y accesorios"); c.setTelefono("999111222"); repos.comerciantes.add(c.getId(),c);
  producto(c,"Polo de algodón","Ropa","39.90","775000000001","M","Azul",12,5);
  producto(c,"Zapatillas urbanas","Calzado","119.90","775000000002","39","Blanco",4,5);
  producto(c,"Mochila casual","Accesorios","69.90","775000000003","Única","Negro",8,3);
 }
 private void producto(Comerciante c,String nombre,String categoria,String precio,String codigo,String talla,String color,int stock,int minimo) {
  var p=new ProductoItem(); p.setComercianteId(c.getId());p.setNombre(nombre);p.setCategoria(categoria);p.setDescripcion("Producto de demostración");p.setPrecioBase(new BigDecimal(precio));facade.crearProducto(p);
  facade.agregarVariante(p.getId(),new VarianteSKU(p.getId(),codigo,talla,color,stock,minimo,new BigDecimal(precio)));
 }
}
