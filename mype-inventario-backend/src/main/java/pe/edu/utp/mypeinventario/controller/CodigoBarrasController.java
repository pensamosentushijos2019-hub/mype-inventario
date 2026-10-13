package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.CodigoBarras;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/codigos-barras")
public class CodigoBarrasController extends CrudController<CodigoBarras> {
    public CodigoBarrasController(Repositories repositories) {
        super(repositories.codigosBarras, "CodigoBarras");
    }
}
