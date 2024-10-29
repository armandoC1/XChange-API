package Trueque.Trueque.dtos.categoria;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaGuardar implements Serializable {
    private String nombre;
}
