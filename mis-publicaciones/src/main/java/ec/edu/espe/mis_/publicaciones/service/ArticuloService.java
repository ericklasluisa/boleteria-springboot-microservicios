package ec.edu.espe.mis_.publicaciones.service;

import ec.edu.espe.mis_.publicaciones.dto.ArticuloCatalogoDto;
import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import ec.edu.espe.mis_.publicaciones.model.Articulo;
import ec.edu.espe.mis_.publicaciones.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearArticulo(Articulo articulo) {

        Articulo articuloGuardado = articuloRepository.save(articulo);

        ArticuloCatalogoDto articuloCatalogoDto = new ArticuloCatalogoDto();

        articuloCatalogoDto.setTitulo(articulo.getTitulo());
        articuloCatalogoDto.setTipo("articulo");
        articuloCatalogoDto.setAnioPublicacion(articulo.getAnioPublicacion());
        articuloCatalogoDto.setEditorial(articulo.getEditorial());
        articuloCatalogoDto.setRevista(articulo.getRevista());
        articuloCatalogoDto.setId_autor(articulo.getAutor().getId());

        //Notificar el evento de catalogo
        producer.publicarArticulo(articuloCatalogoDto);

        return new ResponseDto(
                "artículo registrado correctamente",
                articuloGuardado
        );
    }

    public List<Articulo> listarArticulos() {
        return articuloRepository.findAll();
    }

    public Optional<Articulo> obtenerArticuloPorId(Long id) {
        return articuloRepository.findById(id);
    }

    public Articulo actualizarArticulo(Long id, Articulo articulo) {
        articulo.setId(id);
        return articuloRepository.save(articulo);
    }

    public void eliminarArticulo(Long id) {
        articuloRepository.deleteById(id);
    }
}
