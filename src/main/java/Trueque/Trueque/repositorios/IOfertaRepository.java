package Trueque.Trueque.repositorios;

import Trueque.Trueque.dtos.oferta.OfertaSalida;
import Trueque.Trueque.modelos.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Long> {

    List<Oferta> findByTituloContainingIgnoreCase(String titulo);
}
