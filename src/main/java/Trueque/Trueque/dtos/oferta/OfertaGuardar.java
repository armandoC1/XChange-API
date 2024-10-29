package Trueque.Trueque.dtos.oferta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfertaGuardar implements Serializable {

    private String titulo;

    private String descripcion;

    private String condicion;

    private String ubicacion;

    private List<byte[]> imagenes;

    private Long idCategoria;

    private Long idUsuario;
}
