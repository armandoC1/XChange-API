package Trueque.Trueque.dtos.mensaje;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
public class MensajeGuardar implements Serializable {

    private String contenidoMensaje;  // Contenido del mensaje que se va a guardar

    private Long idCoincidencia;  // ID de la coincidencia donde se envía el mensaje

    private Long idRemitente;  // ID del remitente (quién envía el mensaje)

    private Long idDestinatario;  // ID del destinatario (quién recibe el mensaje)
}
