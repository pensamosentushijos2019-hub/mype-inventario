package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.EtiquetaProducto;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/etiquetas-producto")
public class EtiquetaProductoController extends CrudController<EtiquetaProducto> {
    public EtiquetaProductoController(Repositories repositories) {
        super(repositories.etiquetasProducto, "EtiquetaProducto");
    }
}
