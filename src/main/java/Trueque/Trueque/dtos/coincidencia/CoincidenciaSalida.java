package Trueque.Trueque.dtos.coincidencia;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoincidenciaSalida implements Serializable {


    private Double puntuacionCoincidencia;

    private String estado;

    private Long idOferta;

    private Long idSolicitud;

    private LocalDateTime fechaCoincidencia;
}
