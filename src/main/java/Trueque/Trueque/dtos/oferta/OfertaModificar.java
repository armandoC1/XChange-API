package Trueque.Trueque.dtos.oferta;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class OfertaModificar implements Serializable{

    private Long idOferta;

    private String titulo;

    private String descripcion;

    private String condicion;

    private String ubicacion;

    private String imagenes;

    private String estado;

    private Long idCategoria;

    private Long idUsuario;
}
