package notificaciones.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import notificaciones.dto.NotificationDto;
import notificaciones.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @Autowired
    private NotificationService service;

    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "notification.cola")
    public void recibirNotificacion(String mensajeJson) {
        try {
            NotificationDto dto = mapper.readValue(mensajeJson, NotificationDto.class);
            service.guardarNotificacion(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
