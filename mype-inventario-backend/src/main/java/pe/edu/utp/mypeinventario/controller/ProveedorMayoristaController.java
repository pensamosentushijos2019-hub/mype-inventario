package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.ProveedorMayorista;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorMayoristaController extends CrudController<ProveedorMayorista> {
    public ProveedorMayoristaController(Repositories repositories) {
        super(repositories.proveedores, "ProveedorMayorista");
    }
}
