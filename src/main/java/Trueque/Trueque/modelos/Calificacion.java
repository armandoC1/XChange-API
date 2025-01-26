package Trueque.Trueque.modelos;
import Trueque.Trueque.seguridad.modelos.Usuario;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_calificacion")
    private Long id;

    @Column(name = "puntuacion")
    private Double puntuacion;

    @Column(name = "resena", columnDefinition = "TEXT")
    private String resena;

    @Column(name = "fecha_calificacion")
    private LocalDateTime fechaCalificacion;

    @ManyToOne
    @JoinColumn(name = "id_intercambio", nullable = false)
    private Intercambio intercambio;

    @ManyToOne
    @JoinColumn(name = "id_usuario_calificador", nullable = false)
    private Usuario usuarioCalificador;

    @ManyToOne
    @JoinColumn(name = "id_usuario_calificado", nullable = false)
    private Usuario usuarioCalificado;


    @PrePersist
    protected void onCreate(){
        this.fechaCalificacion = ZonedDateTime.now(ZoneId.of("America/El_Salvador")).toLocalDateTime();
    }
}
