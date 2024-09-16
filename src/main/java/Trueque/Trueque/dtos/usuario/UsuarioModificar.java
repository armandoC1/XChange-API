package Trueque.Trueque.dtos.usuario;

import lombok.*;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioModificar implements Serializable {

    private Long idUsuario;

    private String nombre;

    private String correo;

    private String contrasena;

    private String numeroTelefono;

    private byte[] fotoPerfil;

    private String ubicacion;

    private Double reputacion;

    private Boolean estado;
}
