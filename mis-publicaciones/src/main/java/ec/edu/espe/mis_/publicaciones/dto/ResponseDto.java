package ec.edu.espe.mis_.publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ResponseDto {
    private String message;
    private Object data;

}
