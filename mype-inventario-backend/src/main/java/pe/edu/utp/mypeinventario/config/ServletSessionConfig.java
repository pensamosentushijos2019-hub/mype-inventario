package pe.edu.utp.mypeinventario.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import javax.servlet.SessionTrackingMode;
import java.util.Set;
/** Configuración aplicable también al contenedor Tomcat externo. */
@Configuration
public class ServletSessionConfig {
 @Bean public ServletContextInitializer sessionSettings() {
  return context -> {
   context.setSessionTrackingModes(Set.of(SessionTrackingMode.COOKIE));
   context.getSessionCookieConfig().setHttpOnly(true);
  };
 }
}
