package Trueque.Trueque.dtos.intercambio;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntercambioGuardar implements Serializable {

    private Long idOferta;

    private Long idSolicitud;

    private Long idUsurioOfertante;

    private Long idUsuarioSolicitante;


}
