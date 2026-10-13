package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.RolPermiso;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/roles")
public class RolPermisoController extends CrudController<RolPermiso> {
    public RolPermisoController(Repositories repositories) {
        super(repositories.rolesPermiso, "RolPermiso");
    }
}
