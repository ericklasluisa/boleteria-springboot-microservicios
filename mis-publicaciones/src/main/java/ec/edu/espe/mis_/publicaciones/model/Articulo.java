package ec.edu.espe.mis_.publicaciones.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "articulo")
@Inheritance(strategy = InheritanceType.JOINED)
public class Articulo extends Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "doi", nullable = false, length = 50, unique = true)
    private String doi;

    @Column(name = "revista", nullable = false, length = 50)
    private String revista;

    @Column(name = "volumen", nullable = false, length = 50)
    private String volumen;
    private String numero;
    private String paginas;
    private String mesPublicacion;
    private String tipoArticulo;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    @JsonIgnore
    private Autor autor;
}
