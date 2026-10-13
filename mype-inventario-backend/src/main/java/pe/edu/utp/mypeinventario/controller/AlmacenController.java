package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.Almacen;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/almacenes")
public class AlmacenController extends CrudController<Almacen> {
    public AlmacenController(Repositories repositories) {
        super(repositories.almacenes, "Almacen");
    }
}
