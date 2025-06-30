package ec.edu.espe.mis_.publicaciones.service;

import ec.edu.espe.mis_.publicaciones.dto.AutorDto;
import ec.edu.espe.mis_.publicaciones.dto.ResponseDto;
import ec.edu.espe.mis_.publicaciones.model.Autor;
import ec.edu.espe.mis_.publicaciones.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearAutor(AutorDto dato) {
        // Validar email único
        Optional<Autor> existente = autorRepository.findAll().stream()
            .filter(a -> a.getEmail().equalsIgnoreCase(dato.getEmail()))
            .findFirst();
        if (existente.isPresent()) {
            return new ResponseDto("El email ya está registrado", null);
        }
        Autor autor = new Autor();
        
        // No need to set publication fields for an Author entity
        // These fields should be handled by a Publication entity instead
        
        // Campos específicos de Autor
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());
        autor.setInstitucion(dato.getInstitucion());

        Autor autorGuardado = autorRepository.save(autor);

        //Notificar el evento
        producer.enviarNotificacion(
                "Nuevo autor registrado: " + autor.getNombre() + autor.getApellido(),
                "nuevo-autor"
        );

        return new ResponseDto(
                "Autor registrado correctamente",
                autorGuardado
        );
    }
    
    public ResponseDto actualizarAutor(Long id,AutorDto dato) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No se encontro el" + id +" autor con el id"));
        // Validar email único (excepto el propio autor)
        Optional<Autor> existente = autorRepository.findAll().stream()
            .filter(a -> a.getEmail().equalsIgnoreCase(dato.getEmail()) && !a.getId().equals(id))
            .findFirst();
        if (existente.isPresent()) {
            return new ResponseDto("El email ya está registrado por otro autor", null);
        }
        // Validar orcid único (excepto el propio autor)
        Optional<Autor> orcidExistente = autorRepository.findAll().stream()
            .filter(a -> a.getOrcid().equalsIgnoreCase(dato.getOrcid()) && !a.getId().equals(id))
            .findFirst();
        if (orcidExistente.isPresent()) {
            return new ResponseDto("El ORCID ya está registrado por otro autor", null);
        }
        
        // Solo actualizar campos específicos de Autor
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setNacionalidad(dato.getNacionalidad());
        autor.setTelefono(dato.getTelefono());
        autor.setInstitucion(dato.getInstitucion());

        Autor autorGuardado = autorRepository.save(autor);

        return new ResponseDto(
                "Autor actualizado correctamente",
                autorGuardado
        );
    }
    public ResponseDto listarAutores() {
        List<String> autores = autorRepository.findAll().stream()
                .map(a -> "Autor: " + a.getNombre())
                .collect(Collectors.toList());

        return new ResponseDto("Lista de autores obtenida correctamente", autores);
    }

    public ResponseDto buscarAutorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontro el autor con el id" + id));

        return new ResponseDto("Autor encontrado correctamente", autor);
    }

}
