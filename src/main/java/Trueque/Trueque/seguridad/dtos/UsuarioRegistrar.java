package Trueque.Trueque.seguridad.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioRegistrar {

    private String nombre;

    private String correo;

    private String contrasena;

    private String numeroTelefono;

    private byte[] fotoPerfil;

    private String ubicacion;

    private Integer idRol;
}