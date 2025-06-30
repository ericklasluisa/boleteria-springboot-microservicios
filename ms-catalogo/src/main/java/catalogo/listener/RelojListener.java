package catalogo.listener;

import catalogo.dto.HoraServidorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelojListener {
    @Autowired
    private ObjectMapper mapper;

    @RabbitListener(queues = "reloj.sincronizacion")
    public void recibirSincronizacion(String mensajeJson) {
        try {
            HoraServidorDto dto = mapper.readValue(mensajeJson, HoraServidorDto.class);
            System.out.println("Hora sincronizada del servidor " + dto.getHoraServidor());
            Long diferencia = dto.getDiferencias().get("ms-catalogo");

            if (diferencia == null) {
                System.out.println("No se recibió diferencia de tiempo para este nodo.");
                return;
            }

            if (diferencia == 0) {
                System.out.println("Tu reloj está sincronizado.");
            } else if (diferencia > 0) {
                System.out.println("Tu reloj está atrasado por " + diferencia + " ms.");
            } else {
                System.out.println("Tu reloj está adelantado por " + Math.abs(diferencia) + " ms.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
