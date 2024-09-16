package Trueque.Trueque.dtos.solicitud;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class SolicitudSalida implements Serializable {

    private String titulo;

    private String descripcion;

    private String ubicacion;

    private LocalDateTime fechaCreacion;

    private String estado;

    private Long idCategoria;

    private Long idUsuario;

}
