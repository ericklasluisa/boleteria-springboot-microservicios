package ec.edu.espe.mis_.publicaciones.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name ="libro")

public class Libro extends Publicacion {

    private String genero;
    private String numeroPaginas;
    private String edicion;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
}
