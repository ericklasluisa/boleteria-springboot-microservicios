package catalogo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue librosCola() {
        return QueueBuilder.durable("libros.cola").build();
    }

    @Bean
    public Queue articulosCola() {
        return QueueBuilder.durable("articulos.cola").build();
    }
}