package ec.edu.espe.mis_.publicaciones.config;

import ec.edu.espe.mis_.publicaciones.service.RelojProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Autowired
    private RelojProducer relojProducer;

    @Scheduled(fixedRate = 10000)
    public void reportarHora() {
        try {
            relojProducer.enviarReloj();
            System.out.println("Nodo: ms-publicaciones -> Enviando hora");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
