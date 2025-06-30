package catalogo.listener;

import catalogo.dto.ArticuloDto;
import catalogo.dto.LibroDto;
import catalogo.service.CatalogoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogoListener {
    @Autowired
    private CatalogoService service;

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "libros.cola")
    public void publicarLibro(String mensajeJson) {
        try {
            LibroDto dto = mapper.readValue(mensajeJson, LibroDto.class);
            service.guardarLibro(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "articulos.cola")
    public void publicarArticulo(String mensajeJson) {
        try {
            ArticuloDto dto = mapper.readValue(mensajeJson, ArticuloDto.class);
            service.guardarArticulo(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
