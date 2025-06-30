package ec.edu.espe.mis_.publicaciones.repository;

import ec.edu.espe.mis_.publicaciones.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}
