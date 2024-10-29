package Trueque.Trueque.dtos.oferta;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfertaSalida implements Serializable {

    private Long idOferta;

    private String titulo;

    private String descripcion;

    private String condicion;

    private String ubicacion;

    private List<byte[]> imagenes;

    private LocalDateTime fechaCreacion;

    private String estado;

    private Long idUsuario;

    private Long idCategoria;

}
