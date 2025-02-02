package Trueque.Trueque.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "coincidencias")
public class Coincidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coincidencia")
    private Long idCoincidencia;

    @Column(name = "puntuacion_coincidencia")
    private Double puntuacionCoincidencia;

    @Column(name = "fecha_coincidencia")
    private LocalDateTime fechaCoincidencia;

    @Column(name = "estado", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @PrePersist
    protected void onCreate(){
        this.fechaCoincidencia = ZonedDateTime.now(ZoneId.of("America/El_Salvador")).toLocalDateTime();
        this.estado = "pendiente";
    }

}
