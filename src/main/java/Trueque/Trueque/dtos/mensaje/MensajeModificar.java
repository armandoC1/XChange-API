package Trueque.Trueque.dtos.mensaje;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MensajeModificar implements Serializable {

    private Long id;
    private String contenidoMensaje;
}
