package Trueque.Trueque.modelos;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Para que el ID se genere automáticamente
    private Long id;

    @Column(name = "contenido_mensaje", columnDefinition = "TEXT")
    private String contenidoMensaje;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @ManyToOne
    @JoinColumn(name = "id_oferta")  // Definimos la relación con Oferta
    private Oferta oferta;

    @ManyToOne
    @JoinColumn(name = "id_remitente")  // Relación con Usuario como remitente
    private Usuario remitente;

    @ManyToOne
    @JoinColumn(name = "id_destinatario")  // Relación con Usuario como destinatario
    private Usuario destinatario;


    @PrePersist
    protected void onCreate() {
        this.fechaEnvio = LocalDateTime.now();
    }
}
