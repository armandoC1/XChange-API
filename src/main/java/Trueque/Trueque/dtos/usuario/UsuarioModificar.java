package Trueque.Trueque.dtos.usuario;

import lombok.*;
import java.io.*;
import java.time.*;

@Setter
@Getter
public class UsuarioModificar implements Serializable{

    private Long idUsuario;

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
