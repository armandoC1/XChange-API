package Trueque.Trueque.dtos.oferta;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class OfertaSalida {

    private Long idOferta;

    private String titulo;

    private String descripcion;

    private String condicion;

    private String ubicacion;

    private String imagenes;

    private LocalDateTime fechaCreacion;

    private String estado;

    private Long idUsuario;

    private Long idCategoria;

}
