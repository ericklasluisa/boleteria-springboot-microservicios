package ec.edu.espe.sincronizacion.services;

import ec.edu.espe.sincronizacion.dto.HoraClienteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SincronizacionService {
    @Autowired
    private SincronizacionProducer sincronizacionProducer;

    private final Map<String, Long> tiemposClientes = new ConcurrentHashMap<>();
    Map<String, Long> diferenciasClientes = new ConcurrentHashMap<>();

    private static int INVERTALO_SEGUNDOS = 10;

    public void registrarTiempo(HoraClienteDto dto) {
        tiemposClientes.put(dto.getNombreNodo(), dto.getHoraEnviada());
        System.out.println(dto.getNombreNodo() + " tiempo de enviada " + dto.getHoraEnviada());
    }

    public void sincronizarRelojes() {
        if (tiemposClientes.size() >= 2) {
            long ahora = Instant.now().toEpochMilli();
            long promedio = (ahora + tiemposClientes.values().stream().mapToLong(Long::longValue).sum())
                    / (tiemposClientes.size() + 1);

            // Calcular y registrar diferencias de tiempo
            diferenciasClientes.clear();
            for (Map.Entry<String, Long> entry : tiemposClientes.entrySet()) {
                long diferencia = promedio - entry.getValue();
                diferenciasClientes.put(entry.getKey(), diferencia);
                System.out.println("Nodo: " + entry.getKey() + " debe ajustar: " + diferencia + " ms");
            }

            tiemposClientes.clear();

            enviarAjusteRelojes(promedio);
        }
    }

    public void enviarAjusteRelojes(long horaServidor) {
        System.out.println("Ajustando relojes a la hora: " + horaServidor);
        sincronizacionProducer.enviarSincronizacion(horaServidor, diferenciasClientes);
    }
}
