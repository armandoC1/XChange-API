package Trueque.Trueque.dtos.categoria;

import lombok.*;
import java.io.Serializable;

@Setter
@Getter
public class CategoriaModificar implements Serializable {
    private Long id;
    private String nombre;
}
