package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.Lote;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/lotes")
public class LoteController extends CrudController<Lote> {
    public LoteController(Repositories repositories) {
        super(repositories.lotes, "Lote");
    }
}
