package pe.edu.utp.mypeinventario.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller public class RootController {
 @GetMapping("/") public String raiz() {return "redirect:/index.html";}
}
