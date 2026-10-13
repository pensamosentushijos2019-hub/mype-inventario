package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.Marca;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController extends CrudController<Marca> {
    public MarcaController(Repositories repositories) {
        super(repositories.marcas, "Marca");
    }
}
