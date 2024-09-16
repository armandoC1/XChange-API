package Trueque.Trueque.dtos.solicitud;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class SolicitudGuardar implements Serializable {

    private String titulo;

    private String descripcion;

    private String ubicacion;

    private Long idUsuario;

    private Long idCategoria;
}
