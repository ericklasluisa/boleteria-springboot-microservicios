package catalogo.service;

import catalogo.dto.NotificationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
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
}
