package Trueque.Trueque.dtos.categoria;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaSalida implements Serializable {
    private Long id;
    private String nombre;
}
