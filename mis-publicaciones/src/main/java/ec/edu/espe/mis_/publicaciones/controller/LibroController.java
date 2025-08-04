package ec.edu.espe.mis_.publicaciones.controller;

import ec.edu.espe.mis_.publicaciones.model.Autor;
import ec.edu.espe.mis_.publicaciones.model.Libro;
import ec.edu.espe.mis_.publicaciones.repository.AutorRepository;
import ec.edu.espe.mis_.publicaciones.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libro")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorRepository autorRepository;

    @PostMapping
    public ResponseEntity<?> crearLibro(@RequestBody Libro libro) {
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            return ResponseEntity.badRequest().body("Debe especificar el id del autor");
        }
        Autor autor = autorRepository.findById(libro.getAutor().getId()).orElse(null);
        if (autor == null) {
            return ResponseEntity.badRequest().body("Autor no encontrado");
        }
        libro.setAutor(autor);
        return ResponseEntity.ok(libroService.crearLibro(libro));
    }

    @GetMapping
    public List<Libro> listarLibros() {
        return libroService.listarLibros();
    }

    @GetMapping("/{id}")
    public Optional<Libro> obtenerLibroPorId(@PathVariable Long id) {
        return libroService.obtenerLibroPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarLibro(@PathVariable Long id, @RequestBody Libro libro) {
        if (libro.getAutor() != null && libro.getAutor().getId() != null) {
            Autor autor = autorRepository.findById(libro.getAutor().getId()).orElse(null);
            if (autor == null) {
                return ResponseEntity.badRequest().body("Autor no encontrado");
            }
            libro.setAutor(autor);
        }
        return ResponseEntity.ok(libroService.actualizarLibro(id, libro));
    }

    @DeleteMapping("/{id}")
    public void eliminarLibro(@PathVariable Long id) {
        libroService.eliminarLibro(id);
    }
}
