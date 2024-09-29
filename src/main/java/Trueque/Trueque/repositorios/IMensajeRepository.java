package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.Mensaje;
import Trueque.Trueque.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMensajeRepository extends JpaRepository<Mensaje, Long> {

    // Ajustado para usar 'idOferta' correctamente
    List<Mensaje> findByOfertaIdOfertaAndRemitenteIdUsuarioAndDestinatarioIdUsuario(Long ofertaId, Long remitenteId, Long destinatarioId);


    // También obtener todos los mensajes por oferta
    List<Mensaje> findByOfertaIdOferta(Long idOferta);
}
