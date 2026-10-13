package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.ListaPrecio;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/listas-precio")
public class ListaPrecioController extends CrudController<ListaPrecio> {
    public ListaPrecioController(Repositories repositories) {
        super(repositories.listasPrecio, "ListaPrecio");
    }
}
