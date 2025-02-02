package Trueque.Trueque.dtos.mensaje;
import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeGuardar implements Serializable {

    private String contenidoMensaje;
    private Long idRemitente;
    private Long idDestinatario;
}
