package catalogo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticuloDto {
    // atributos generales
    private String titulo;
    private String tipo;
    private int anioPublicacion;
    private String editorial;
    // atributos del libro
    private String revista;

    private Long id_autor;
}
