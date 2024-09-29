package Trueque.Trueque.dtos.usuario;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {
    private String correo;
    private String contrasena;
}
