package catalogo.service;

import catalogo.dto.ArticuloDto;
import catalogo.dto.LibroDto;
import catalogo.model.Catalogo;
import catalogo.repository.CatalogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {
    @Autowired
    private CatalogoRepository catalogoRepository;

    @Autowired
    private NotificationProducer producer;

    public void guardarLibro(LibroDto libroDto) {
        Catalogo libro = new Catalogo();

        libro.setTitulo(libroDto.getTitulo());
        libro.setTipo(libroDto.getTipo());
        libro.setAnioPublicacion(libroDto.getAnioPublicacion());
        libro.setEditorial(libroDto.getEditorial());
        libro.setGenero(libroDto.getGenero());
        libro.setId_autor(libroDto.getId_autor());
        catalogoRepository.save(libro);

        //Notificar el evento de notificación
        producer.enviarNotificacion(
                "Nuevo libro registrado: " + libro.getTitulo(),
                "nuevo-libro"
        );

    }

    public void guardarArticulo(ArticuloDto articuloDto) {
        Catalogo articulo = new Catalogo();

        articulo.setTitulo(articuloDto.getTitulo());
        articulo.setTipo(articuloDto.getTipo());
        articulo.setAnioPublicacion(articuloDto.getAnioPublicacion());
        articulo.setEditorial(articuloDto.getEditorial());
        articulo.setRevista(articuloDto.getRevista());
        articulo.setId_autor(articuloDto.getId_autor());
        catalogoRepository.save(articulo);

        //Notificar el evento de notificación
        producer.enviarNotificacion(
                "Nuevo articulo registrado: " + articulo.getTitulo(),
                "nuevo-articulo"
        );

    }
}
