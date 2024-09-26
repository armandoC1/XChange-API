package Trueque.Trueque.dtos.mensaje;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
public class MensajeSalida implements Serializable {

    private Long id;  // ID del mensaje

    private String contenidoMensaje;  // Contenido del mensaje

    private LocalDateTime fechaEnvio;  // Fecha de envío del mensaje

    private Long idCoincidencia;  // ID de la coincidencia

    private Long idRemitente;  // ID del remitente

    private Long idDestinatario;  // ID del destinatario
}
