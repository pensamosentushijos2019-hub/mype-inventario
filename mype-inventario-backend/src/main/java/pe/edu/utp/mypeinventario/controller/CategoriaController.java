package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.Categoria;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController extends CrudController<Categoria> {
    public CategoriaController(Repositories repositories) {
        super(repositories.categorias, "Categoria");
    }
}
