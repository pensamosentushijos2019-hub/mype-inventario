package pe.edu.utp.mypeinventario.controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.*;
import pe.edu.utp.mypeinventario.service.AuthService;
import java.util.Map;
@RestController @RequestMapping("/api/auth") public class AuthController {
 private final AuthService auth; public AuthController(AuthService auth){this.auth=auth;}
 public record LoginRequest(String email,String password) {}
 @PostMapping("/login") public ResponseEntity<?> login(@RequestBody LoginRequest req,HttpServletRequest request) {
  try {
   var user=auth.login(req.email(),req.password());
   var previous=request.getSession(false);if(previous!=null) previous.invalidate();
   request.getSession(true).setAttribute("user",user);return ResponseEntity.ok(user);
  } catch(IllegalArgumentException e) {return ResponseEntity.status(401).body(Map.of("error",e.getMessage()));}
 }
 @GetMapping("/me") public ResponseEntity<?> me(HttpServletRequest request) {
  var session=request.getSession(false);return session==null || session.getAttribute("user")==null?ResponseEntity.status(401).body(Map.of("error","Inicia sesión")):ResponseEntity.ok(session.getAttribute("user"));
 }
 @PostMapping("/logout") public Map<String,String> logout(HttpServletRequest request) {var s=request.getSession(false);if(s!=null)s.invalidate();return Map.of("mensaje","Sesión cerrada");}
}
