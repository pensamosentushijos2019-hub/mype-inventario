package pe.edu.utp.mypeinventario.dto;
import java.util.List;
public record VentaRequest(String comercianteId,String metodoPago,List<Item> items) {
 public record Item(String varianteId,Integer cantidad) {}
}
