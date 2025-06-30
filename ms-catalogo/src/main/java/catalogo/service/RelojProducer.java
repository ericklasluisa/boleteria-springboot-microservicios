package catalogo.service;

import catalogo.dto.HoraClienteDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RelojProducer {

    @Autowired
    private AmqpTemplate template;

    @Autowired
    private ObjectMapper mapper;

    private static final String nombreNodo = "ms-catalogo";

    public void enviarReloj() {

        try {
            HoraClienteDto dto = new HoraClienteDto(nombreNodo, Instant.now().toEpochMilli());
            String json = mapper.writeValueAsString(dto);
            template.convertAndSend("reloj.solicitud", json);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
