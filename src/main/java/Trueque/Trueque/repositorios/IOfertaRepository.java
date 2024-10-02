package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RestResource;


@RestResource(exported = false)
@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Long> {

    List<Oferta> findByTituloContainingIgnoreCase(String titulo);
}
