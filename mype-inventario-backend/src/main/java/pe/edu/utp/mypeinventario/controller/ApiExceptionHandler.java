package pe.edu.utp.mypeinventario.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Map;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler({IllegalArgumentException.class,ArithmeticException.class})
 public ResponseEntity<?> invalida(RuntimeException e) {return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()==null?"Solicitud inválida":e.getMessage()));}
 @ExceptionHandler(DataIntegrityViolationException.class)
 public ResponseEntity<?> conflicto(DataIntegrityViolationException e) {return ResponseEntity.status(409).body(Map.of("error","Código de barras duplicado o relación inválida"));}
}
