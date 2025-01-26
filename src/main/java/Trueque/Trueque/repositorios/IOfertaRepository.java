package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RestResource;


@RestResource(exported = false)
@Repository
public interface IOfertaRepository extends JpaRepository<Oferta, Long> {

    List<Oferta> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT o FROM Oferta o WHERE o.usuario.id = :userId")
    List<Oferta> findOfertasByUsuarioId(@Param("userId") Long userId);
}
