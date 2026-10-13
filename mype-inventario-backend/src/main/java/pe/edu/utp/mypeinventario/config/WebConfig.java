package pe.edu.utp.mypeinventario.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.*;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import java.util.*;
@Configuration public class WebConfig implements WebMvcConfigurer {
 private final Set<String> origins;
 public WebConfig(@Value("${app.allowed-origins:http://localhost:8080,http://127.0.0.1:8080,http://localhost:3000,http://localhost:5173}") String values) {origins=new HashSet<>(Arrays.asList(values.split(",")));}
 @Override public void addInterceptors(InterceptorRegistry registry) {
  registry.addInterceptor(new HandlerInterceptor() {
   private boolean reject(HttpServletResponse response,int code,String text) throws IOException {
    response.setStatus(code);response.setContentType("application/json;charset=UTF-8");response.getWriter().write("{\"error\":\""+text+"\"}");return false;
   }
   @Override public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
    response.setHeader("Cache-Control","no-store");
    if(!Set.of("GET","HEAD","OPTIONS").contains(request.getMethod())) {
     String origin=request.getHeader("Origin");
     if(origin!=null && !origins.contains(origin)) return reject(response,403,"Origen no autorizado");
     if(!"web".equals(request.getHeader("X-Mype-Client"))) return reject(response,403,"Falta cabecera X-Mype-Client");
    }
    String route=request.getRequestURI().substring(request.getContextPath().length());
    if(route.equals("/api/auth/login")) return true;
    var s=request.getSession(false);
    if(s==null || s.getAttribute("user")==null) return reject(response,401,"Inicia sesión para continuar");
    // El avance trabaja con una tienda. El alta multiempresa queda fuera del alcance.
    if(route.equals("/api/comerciantes") && !"GET".equals(request.getMethod())) return reject(response,403,"El avance utiliza una tienda de demostración");
    return true;
   }
  }).addPathPatterns("/api/**");
 }
}
