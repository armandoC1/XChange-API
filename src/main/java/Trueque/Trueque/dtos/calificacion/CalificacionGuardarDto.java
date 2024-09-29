package Trueque.Trueque.dtos.calificacion;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class CalificacionGuardarDto implements Serializable {

    private Double puntuacion;
    private String resena;
    private Long idIntercambio;
    private Long idUsuarioCalificador;
    private Long idUsuarioCalificado;
}
//programacion reactiva