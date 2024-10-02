package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;


@RestResource(exported = false)
@Repository
public interface IMensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findByOfertaIdOfertaAndRemitenteIdUsuarioAndDestinatarioIdUsuario(Long ofertaId, Long remitenteId, Long destinatarioId);

    List<Mensaje> findByOfertaIdOferta(Long idOferta);
}
