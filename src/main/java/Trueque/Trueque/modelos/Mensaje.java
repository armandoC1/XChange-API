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
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contenido_mensaje", columnDefinition = "TEXT")
    private String contenidoMensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "id_oferta")
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_remitente")
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "id_destinatario")
    private Usuario destinatario;


    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = ZonedDateTime.now(ZoneId.of("America/El_Salvador")).toLocalDateTime();
    }
}
