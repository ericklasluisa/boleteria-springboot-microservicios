package ec.edu.espe.mis_.publicaciones.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.mis_.publicaciones.dto.ArticuloCatalogoDto;
import ec.edu.espe.mis_.publicaciones.dto.LibroCatalogoDto;
import ec.edu.espe.mis_.publicaciones.dto.NotificationDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacionProducer {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper mapper;

    public void enviarNotificacion(String mensaje, String tipo) {
        try {
            NotificationDto dto = new NotificationDto(mensaje, tipo);
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("notification.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publicarLibro(LibroCatalogoDto dto) {
        try {
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("libros.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publicarArticulo(ArticuloCatalogoDto dto) {
        try {
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("articulos.cola", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
