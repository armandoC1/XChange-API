package Trueque.Trueque.dtos.mensaje;

import java.io.Serializable;
import lombok.*;

@Getter
@Setter
public class MensajeModificar implements Serializable {

    private Long id;  // ID del mensaje que se va a modificar

    private String contenidoMensaje;  // El nuevo contenido del mensaje
}
