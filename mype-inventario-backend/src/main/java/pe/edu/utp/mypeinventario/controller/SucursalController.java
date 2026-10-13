package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.Sucursal;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController extends CrudController<Sucursal> {
    public SucursalController(Repositories repositories) {
        super(repositories.sucursales, "Sucursal");
    }
}
