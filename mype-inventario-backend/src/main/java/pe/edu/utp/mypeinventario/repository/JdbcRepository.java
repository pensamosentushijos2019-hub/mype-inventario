package pe.edu.utp.mypeinventario.repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.*;
/** DAO JDBC. Payload conserva las clases del primer avance y las columnas
 * relacionales del núcleo añaden claves foráneas, unicidad y controles SQL. */
public class JdbcRepository<T> implements Repository<T> {
 private final JdbcTemplate jdbc;
 private final ObjectMapper mapper;
 private final Class<T> type;
 private final String table;
 private final LinkedHashMap<String,String> columns;
 public JdbcRepository(JdbcTemplate jdbc, ObjectMapper mapper, Class<T> type, String table, LinkedHashMap<String,String> columns) {
  this.jdbc=jdbc; this.mapper=mapper; this.type=type; this.table=table; this.columns=columns;
 }
 private T decode(String json) {
  try { return mapper.readValue(json,type); }
  catch(Exception e) { throw new IllegalStateException("No se pudo leer "+table,e); }
 }
 public T add(String id,T entity) {
  try {
   var tree=mapper.valueToTree(entity);
   List<Object> values=new ArrayList<>(); values.add(id); values.add(mapper.writeValueAsString(entity));
   for(String field:columns.values()) {
    var node=tree.get(field);
    values.add(node==null || node.isNull() ? null : node.isNumber() ? node.decimalValue() : node.asText());
   }
   String names="id,payload"+(columns.isEmpty()?"":","+String.join(",",columns.keySet()));
   jdbc.update("MERGE INTO "+table+" ("+names+") KEY(id) VALUES ("+String.join(",",Collections.nCopies(values.size(),"?"))+")",values.toArray());
   return entity;
  } catch(com.fasterxml.jackson.core.JsonProcessingException e) { throw new IllegalStateException("No se pudo guardar "+table,e); }
 }
 public T getById(String id) {
  var rows=jdbc.query("SELECT payload FROM "+table+" WHERE id=?",(rs,n)->decode(rs.getString(1)),id);
  return rows.isEmpty()?null:rows.get(0);
 }
 public T lock(String id) {
  var rows=jdbc.query("SELECT payload FROM "+table+" WHERE id=? FOR UPDATE",(rs,n)->decode(rs.getString(1)),id);
  return rows.isEmpty()?null:rows.get(0);
 }
 public List<T> getAll() { return jdbc.query("SELECT payload FROM "+table+" ORDER BY id",(rs,n)->decode(rs.getString(1))); }
 public boolean delete(String id) { return jdbc.update("DELETE FROM "+table+" WHERE id=?",id)>0; }
}
