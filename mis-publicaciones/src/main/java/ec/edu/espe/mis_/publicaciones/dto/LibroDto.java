package ec.edu.espe.mis_.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto {

    // atributos del padre
    private Long id;
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    // atributis del libro
    private String genero;
    private String numeroPaginas;
    private String edicion;

    private String id_autor;

}