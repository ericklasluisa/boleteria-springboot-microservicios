package notificaciones.service;

import notificaciones.dto.NotificationDto;
import notificaciones.entity.Notificacion;
import notificaciones.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repository;

    public void guardarNotificacion(NotificationDto dto) {
        Notificacion notificacion = new Notificacion();

        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipo(dto.getTipo());
        notificacion.setFecha(LocalDateTime.now());
        repository.save(notificacion);
    }

}
