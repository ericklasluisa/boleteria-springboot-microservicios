package ec.edu.espe.mis_.publicaciones.service;

import ec.edu.espe.mis_.publicaciones.dto.ArticuloCatalogoDto;
import ec.edu.espe.mis_.publicaciones.dto.LibroCatalogoDto;
import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import ec.edu.espe.mis_.publicaciones.model.Libro;
import ec.edu.espe.mis_.publicaciones.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearLibro(Libro libro) {

        Libro libroGuardado = libroRepository.save(libro);

        LibroCatalogoDto libroCatalogoDto = new LibroCatalogoDto();

        libroCatalogoDto.setTitulo(libro.getTitulo());
        libroCatalogoDto.setTipo("articulo");
        libroCatalogoDto.setAnioPublicacion(libro.getAnioPublicacion());
        libroCatalogoDto.setEditorial(libro.getEditorial());
        libroCatalogoDto.setGenero(libro.getGenero());
        libroCatalogoDto.setId_autor(libro.getAutor().getId());

        //Notificar el evento de catalogo
        producer.publicarLibro(libroCatalogoDto);

        return new ResponseDto(
                "libro registrado correctamente",
                libroGuardado
        );
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerLibroPorId(Long id) {
        return libroRepository.findById(id);
    }

    public Libro obtenerLibroSinRecursion(Long id) {
        Optional<Libro> libroOpt = libroRepository.findById(id);
        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            if (libro.getAutor() != null) {
                // Prevent recursion by clearing the books list in author
                libro.getAutor().setLibros(null);
            }
            return libro;
        }
        return null;
    }

    public Libro actualizarLibro(Long id, Libro libro) {
        Optional<Libro> libroExistenteOpt = libroRepository.findById(id);
        if (libroExistenteOpt.isPresent()) {
            Libro libroExistente = libroExistenteOpt.get();
            // Actualiza todos los campos, incluidos los heredados
            libroExistente.setTitulo(libro.getTitulo());
            libroExistente.setAnioPublicacion(libro.getAnioPublicacion());
            libroExistente.setEditorial(libro.getEditorial());
            libroExistente.setIsbn(libro.getIsbn());
            libroExistente.setResumen(libro.getResumen());
            libroExistente.setIdioma(libro.getIdioma());
            libroExistente.setGenero(libro.getGenero());
            libroExistente.setNumeroPaginas(libro.getNumeroPaginas());
            libroExistente.setEdicion(libro.getEdicion());
            libroExistente.setAutor(libro.getAutor());
            return libroRepository.save(libroExistente);
        } else {
            libro.setId(id);
            return libroRepository.save(libro);
        }
    }

    public void eliminarLibro(Long id) {
        libroRepository.deleteById(id);
    }
}
