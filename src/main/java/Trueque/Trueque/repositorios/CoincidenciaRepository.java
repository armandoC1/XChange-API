package Trueque.Trueque.repositorios;

import org.springframework.stereotype.Repository;
import Trueque.Trueque.modelos.Coincidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;


@RestResource(exported = false)
@Repository
public interface CoincidenciaRepository extends JpaRepository<Coincidencia, Long> {
}
