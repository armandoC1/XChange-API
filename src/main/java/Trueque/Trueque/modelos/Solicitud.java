package Trueque.Trueque.modelos;

import jakarta.persistence.*;
import lombok.*;
import Trueque.Trueque.seguridad.modelos.Usuario;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "solicitudes")
public class Solicitud {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_solicitud")
    private Long idSolicitud;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private List<byte[]> imagenes;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_solicitante", nullable = false)
    private Usuario solicitante;

    @ManyToOne
    @JoinColumn(name = "id_destinatario", nullable = false)
    private Usuario destinatario;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "solicitud")
    private List<Coincidencia> coincidencias;

    @OneToMany(mappedBy = "solicitud")
    private List<Intercambio> intercambios;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = ZonedDateTime.now(ZoneId.of("America/El_Salvador")).toLocalDateTime();
        this.estado = "activo";
    }
}
