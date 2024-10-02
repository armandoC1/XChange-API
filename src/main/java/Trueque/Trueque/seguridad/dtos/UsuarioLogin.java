package Trueque.Trueque.seguridad.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioLogin {
    private String correo;
    private String contrasena;
}