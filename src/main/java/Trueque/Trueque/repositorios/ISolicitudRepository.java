package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISolicitudRepository extends JpaRepository<Solicitud, Long> {

}
