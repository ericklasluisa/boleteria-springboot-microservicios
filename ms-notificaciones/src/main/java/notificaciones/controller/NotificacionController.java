package notificaciones.controller;

import notificaciones.dto.NotificationDto;
import notificaciones.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacion")
public class NotificacionController {
    @Autowired
    private NotificationService service;

    @PostMapping
    public void crearNotificacion(@RequestBody NotificationDto dto) {
        service.guardarNotificacion(dto);
    }

}
