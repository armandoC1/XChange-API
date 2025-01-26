package Trueque.Trueque.dtos.solicitud;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SolicitudGuardar implements Serializable {

    private String titulo;
    private String descripcion;
    private String ubicacion;
    private List<byte[]> imagenes;
    private Long idSolicitante;
    private Long idDestinatario;
    private Long idCategoria;
}
