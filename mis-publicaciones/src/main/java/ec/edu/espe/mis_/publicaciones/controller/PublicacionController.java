package ec.edu.espe.mis_.publicaciones.controller;

import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import ec.edu.espe.mis_.publicaciones.model.Publicacion;
import ec.edu.espe.mis_.publicaciones.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @GetMapping
    public ResponseDto listarPublicaciones() {
        return publicacionService.listarPublicaciones();
    }
}
