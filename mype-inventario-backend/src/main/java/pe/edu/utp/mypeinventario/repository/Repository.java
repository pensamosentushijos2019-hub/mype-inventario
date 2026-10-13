package pe.edu.utp.mypeinventario.repository;
import java.util.List;
/** Contrato DAO usado por controladores y servicios. */
public interface Repository<T> {
 T add(String id, T entity);
 T getById(String id);
 List<T> getAll();
 boolean delete(String id);
}
