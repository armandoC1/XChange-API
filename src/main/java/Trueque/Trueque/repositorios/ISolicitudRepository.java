package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported = false)
@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Long> {

//    List<Solicitud> findByUsuario_Id(Long idDestinatario);
//
//    List<Solicitud> findByDestinatario_IdUsuarioAndEstado(Long idDestinatario, String estado);
//
//    List<Solicitud> findBySolicitante_Id(Long idSolicitante);

    List<Solicitud> findByDestinatarioIdUsuario(Long idUsuario);

}

