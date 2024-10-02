package Trueque.Trueque.modelos;
import Trueque.Trueque.seguridad.modelos.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "intercambios")
public class Intercambio {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_intercambio")
    private Long idIntercambio;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_completado")
    private LocalDateTime fechaCompletado;

    @ManyToOne
    @JoinColumn(name = "id_oferta", nullable = false)
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "id_usuario_ofertante")
    private Usuario usuarioOfertante;

    @ManyToOne
    @JoinColumn(name = "id_usuario_solicitante", nullable = false )
    private Usuario usuarioSolicitante;

    @PrePersist
    protected void onCreate(){
        this.estado = "completado";
        this.fechaCompletado = LocalDateTime.now();
    }
}
