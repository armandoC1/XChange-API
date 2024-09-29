package Trueque.Trueque.dtos.mensaje;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
public class MensajeGuardar implements Serializable {

    private String contenidoMensaje;
    private Long idOferta;
    private Long idRemitente;
    private Long idDestinatario;
}
