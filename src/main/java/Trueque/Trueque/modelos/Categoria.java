package Trueque.Trueque.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    private String nombre;

    @OneToMany(mappedBy = "categoria")
    private List<Solicitud> solicitudes;

    @OneToMany(mappedBy = "categoria")
    private List<Oferta> ofertas;
}
