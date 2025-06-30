package ec.edu.espe.mis_.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDto {
    
    // Campos específicos de Autor
    private String nombre;
    private String apellido;
    private String email;
    private String orcid;
    private String nacionalidad;
    private String telefono;
    private String institucion;
}
