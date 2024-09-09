package Trueque.Trueque.dtos.usuario;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioGuardar  implements Serializable{

    private String nombre;

    private String correo;

    private String contrasena;

    private String numeroTelefono;

    private String fotoPerfil;

    private String ubicacion;

    private LocalDateTime fechaCreacion;

    private Double reputacion;

    private Boolean estado;
}
