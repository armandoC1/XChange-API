package Trueque.Trueque.dtos.solicitud;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class SolicitudModificar implements Serializable {

    private Long idSolicitud;

    private String titulo;

    private String descripcion;

    private String ubicacion;

    private String estado;

    private Long idUsuario;

    private Long idCategoria;

}
