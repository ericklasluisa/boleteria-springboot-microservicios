package catalogo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity()
public class Catalogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 50)
    private String titulo;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo;

    @Column(name = "anioPublicacion", nullable = false, length = 4)
    private int anioPublicacion;

    @Column(name = "editorial", nullable = false, length = 50)
    private String editorial;

    // Solo para Libros
    @Column(name = "genero", nullable = true, length = 50)
    private String genero;

    // Solo para artículos
    @Column(name = "revista", nullable = true, length = 50)
    private String revista;

    @Column(name = "id_autor", nullable = false, length = 50)
    private Long id_autor;
}
