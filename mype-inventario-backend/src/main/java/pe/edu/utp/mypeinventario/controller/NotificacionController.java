package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.Notificacion;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController extends CrudController<Notificacion> {
    public NotificacionController(Repositories repositories) {
        super(repositories.notificaciones, "Notificacion");
    }
}
