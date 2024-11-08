package Trueque.Trueque.seguridad.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UsuarioToken {
    private Long idUsuario;
    private String token;
}