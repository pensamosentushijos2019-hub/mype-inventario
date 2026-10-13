package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.MetodoPago;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/metodos-pago")
public class MetodoPagoController extends CrudController<MetodoPago> {
    public MetodoPagoController(Repositories repositories) {
        super(repositories.metodosPago, "MetodoPago");
    }
}
