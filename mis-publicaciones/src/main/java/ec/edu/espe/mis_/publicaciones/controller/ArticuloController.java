package ec.edu.espe.mis_.publicaciones.controller;

import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import ec.edu.espe.mis_.publicaciones.model.Articulo;
import ec.edu.espe.mis_.publicaciones.service.ArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articulos")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @PostMapping
    public ResponseDto crearArticulo(@RequestBody Articulo articulo) {
        return articuloService.crearArticulo(articulo);
    }

    @GetMapping
    public List<Articulo> listarArticulos() {
        return articuloService.listarArticulos();
    }

    @GetMapping("/{id}")
    public Optional<Articulo> obtenerArticuloPorId(@PathVariable Long id) {
        return articuloService.obtenerArticuloPorId(id);
    }

    @PutMapping("/{id}")
    public Articulo actualizarArticulo(@PathVariable Long id, @RequestBody Articulo articulo) {
        return articuloService.actualizarArticulo(id, articulo);
    }

    @DeleteMapping("/{id}")
    public void eliminarArticulo(@PathVariable Long id) {
        articuloService.eliminarArticulo(id);
    }
}
