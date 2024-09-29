package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Intercambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IIntercambioRepository extends JpaRepository<Intercambio, Long> {
}
