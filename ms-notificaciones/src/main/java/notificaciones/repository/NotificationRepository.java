package notificaciones.repository;

import notificaciones.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notificacion, Long> {
}
