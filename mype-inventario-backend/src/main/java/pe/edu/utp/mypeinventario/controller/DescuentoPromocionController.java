package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.DescuentoPromocion;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/descuentos-promocion")
public class DescuentoPromocionController extends CrudController<DescuentoPromocion> {
    public DescuentoPromocionController(Repositories repositories) {
        super(repositories.descuentosPromocion, "DescuentoPromocion");
    }
}
