package Trueque.Trueque.dtos.coincidencia;

import lombok.*;


import java.io.Serializable;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoincidenciaModificar implements Serializable {

    private Long idCoincidencia;

    private Double puntuacionCoincidencia;

    private String estado;

    private Long idOferta;

    private Long idSolicitud;

}
