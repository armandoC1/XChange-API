package Trueque.Trueque.dtos.coincidencia;

import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoincidenciaGuardar implements Serializable {

    private Double puntuacionCoincidencia;

    private Long idOferta;

    private Long idSolicitud;
}
