package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Intercambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RestResource;


@RestResource(exported = false)
@Repository
public interface IIntercambioRepository extends JpaRepository<Intercambio, Long> {
}
