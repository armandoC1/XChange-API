package Trueque.Trueque.repositorios;

import Trueque.Trueque.modelos.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface IMensajeRepository extends JpaRepository<Mensaje, Long> {

    // Buscar mensajes por coincidencia
    List<Mensaje> findByCoincidencia(Coincidencia coincidencia);

    // Buscar mensajes enviados por un remitente
    List<Mensaje> findByRemitente(Usuario remitente);

    // Buscar mensajes recibidos por un destinatario
    List<Mensaje> findByDestinatario(Usuario destinatario);
}
