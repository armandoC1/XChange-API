package Trueque.Trueque.dtos.mensaje;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MensajeSalida implements Serializable {
    private Long id;
    private String contenidoMensaje;
    private LocalDateTime fechaEnvio;
    private Long idOferta;
    private Long idRemitente;
    private Long idDestinatario;
}
