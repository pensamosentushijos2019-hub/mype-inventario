package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.AtributoVariante;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/atributos-variante")
public class AtributoVarianteController extends CrudController<AtributoVariante> {
    public AtributoVarianteController(Repositories repositories) {
        super(repositories.atributosVariante, "AtributoVariante");
    }
}
