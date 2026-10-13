package pe.edu.utp.mypeinventario.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.utp.mypeinventario.domain.ImagenProducto;
import pe.edu.utp.mypeinventario.repository.Repositories;

@RestController
@RequestMapping("/api/imagenes-producto")
public class ImagenProductoController extends CrudController<ImagenProducto> {
    public ImagenProductoController(Repositories repositories) {
        super(repositories.imagenesProducto, "ImagenProducto");
    }
}
