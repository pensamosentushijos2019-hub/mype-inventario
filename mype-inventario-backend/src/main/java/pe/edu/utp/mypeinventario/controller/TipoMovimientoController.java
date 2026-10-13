package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.TipoMovimiento;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/tipos-movimiento")
public class TipoMovimientoController extends CrudController<TipoMovimiento> {
    public TipoMovimientoController(Repositories repositories) {
        super(repositories.tiposMovimiento, "TipoMovimiento");
    }
}
