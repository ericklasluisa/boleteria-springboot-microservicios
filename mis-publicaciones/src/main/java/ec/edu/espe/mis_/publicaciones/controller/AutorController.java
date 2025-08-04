package ec.edu.espe.mis_.publicaciones.controller;

import ec.edu.espe.mis_.publicaciones.dto.AutorDto;
import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import ec.edu.espe.mis_.publicaciones.service.AutorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autor")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseDto listarAutores() {
        return autorService.listarAutores();
    }

    @PostMapping
    public ResponseDto crearAutor(@RequestBody AutorDto dato) {
        return autorService.crearAutor(dato);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@RequestBody AutorDto dato, @PathVariable Long id) {
        return autorService.actualizarAutor(id, dato);
    }
}
