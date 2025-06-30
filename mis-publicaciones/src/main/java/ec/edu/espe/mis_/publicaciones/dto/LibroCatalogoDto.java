package ec.edu.espe.mis_.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroCatalogoDto {

    // atributos generales
    private String titulo;
    private String tipo;
    private int anioPublicacion;
    private String editorial;
    // atributos del libro
    private String genero;

    private Long id_autor;

}
