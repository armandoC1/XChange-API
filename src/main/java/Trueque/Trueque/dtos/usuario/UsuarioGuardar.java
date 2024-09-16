package Trueque.Trueque.dtos.usuario;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioGuardar  implements Serializable{

    private String nombre;

    private String correo;

    private String contrasena;

    private String numeroTelefono;

    private byte[] fotoPerfil;

    private String ubicacion;

}
