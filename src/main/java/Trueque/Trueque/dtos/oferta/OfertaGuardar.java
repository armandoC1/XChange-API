package Trueque.Trueque.dtos.oferta;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class OfertaGuardar implements Serializable {

    private String titulo;

    private String descripcion;

    private String condicion;

    private String ubicacion;

    private String imagenes;

    private Long idCategoria;

    private Long idUsuario;
}
