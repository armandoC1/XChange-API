package Trueque.Trueque.dtos.usuario;

import lombok.*;
import java.io.*;
import java.time.*;

@Getter
@Setter
public class UsuarioSalida implements Serializable {

    private Long idUsuario;

    private String nombre;

    private String correo;

    //private String contrasena; //la pora

    private String numeroTelefono;

    private String fotoPerfil;

    private String ubicacion;

    private LocalDateTime fechaCreacion;

    private Double reputacion;

    private Boolean estado;
}
