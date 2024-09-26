package Trueque.Trueque.dtos.intercambio;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class IntercambioModificar implements Serializable {

    private Long idIntercambio;

    private Long idOferta;

    private Long idSolicitud;

    private Long idUsurioOfertante;

    private Long idUsuarioSolicitante;

}
