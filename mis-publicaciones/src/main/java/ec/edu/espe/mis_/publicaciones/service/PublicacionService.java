package ec.edu.espe.mis_.publicaciones.service;

import ec.edu.espe.mis_.publicaciones.model.Publicacion;
import ec.edu.espe.mis_.publicaciones.repository.LibroRepository;
import ec.edu.espe.mis_.publicaciones.repository.ArticuloRepository;
import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicacionService {

    private static final Logger logger = LoggerFactory.getLogger(PublicacionService.class);

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

    public ResponseDto listarPublicaciones() {
        List<Object> publicaciones = new ArrayList<>();

        try {
            // Debug: verificar si hay libros
            List<ec.edu.espe.mis_.publicaciones.model.Libro> todosLosLibros = libroRepository.findAll();
            logger.info("Total de libros encontrados: " + todosLosLibros.size());

            // Agregar libros con información del autor
            List<Object> libros = todosLosLibros.stream()
                    .map(libro -> {
                        logger.info("Procesando libro: " + libro.getTitulo());
                        return new Object() {
                            public final String tipo = "Libro";
                            public final Long id = libro.getId();
                            public final String titulo = libro.getTitulo();
                            public final int anioPublicacion = libro.getAnioPublicacion();
                            public final String editorial = libro.getEditorial();
                            public final String isbn = libro.getIsbn();
                            public final String resumen = libro.getResumen();
                            public final String idioma = libro.getIdioma();
                            public final String genero = libro.getGenero();
                            public final String numeroPaginas = libro.getNumeroPaginas();
                            public final String edicion = libro.getEdicion();
                            public final String autor = libro.getAutor() != null ?
                                    libro.getAutor().getNombre() + " " + libro.getAutor().getApellido() : "Sin autor";
                        };
                    })
                    .collect(Collectors.toList());

            // Debug: verificar si hay artículos
            List<ec.edu.espe.mis_.publicaciones.model.Articulo> todosLosArticulos = articuloRepository.findAll();
            logger.info("Total de artículos encontrados: " + todosLosArticulos.size());

            // Agregar artículos con información del autor
            List<Object> articulos = todosLosArticulos.stream()
                    .map(articulo -> {
                        logger.info("Procesando artículo: " + articulo.getTitulo());
                        return new Object() {
                            public final String tipo = "Artículo";
                            public final Long id = articulo.getId();
                            public final String titulo = articulo.getTitulo();
                            public final int anioPublicacion = articulo.getAnioPublicacion();
                            public final String editorial = articulo.getEditorial();
                            public final String isbn = articulo.getIsbn();
                            public final String resumen = articulo.getResumen();
                            public final String idioma = articulo.getIdioma();
                            public final String doi = articulo.getDoi();
                            public final String revista = articulo.getRevista();
                            public final String volumen = articulo.getVolumen();
                            public final String numero = articulo.getNumero();
                            public final String paginas = articulo.getPaginas();
                            public final String mesPublicacion = articulo.getMesPublicacion();
                            public final String tipoArticulo = articulo.getTipoArticulo();
                            public final String autor = articulo.getAutor() != null ?
                                    articulo.getAutor().getNombre() + " " + articulo.getAutor().getApellido() : "Sin autor";
                        };
                    })
                    .collect(Collectors.toList());

            publicaciones.addAll(libros);
            publicaciones.addAll(articulos);

            logger.info("Total de publicaciones devueltas: " + publicaciones.size());

            return new ResponseDto("Publicaciones obtenidas correctamente", publicaciones);

        } catch (Exception e) {
            logger.error("Error al obtener publicaciones: ", e);
            return new ResponseDto("Error al obtener publicaciones: " + e.getMessage(), new ArrayList<>());
        }
    }
}
