package Trueque.Trueque.modelos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario" ,nullable = false)
    private String nombre;

    @Column(name = "correo_electronico" ,nullable = false)
    private String correo;

    @Column(name = "contrasena" ,nullable = false)
    private String contrasena;

    @Column(name = "numero_telefono" ,nullable = false)
    private String numeroTelefono;

    @Lob
    @Column(name = "foto_perfil", columnDefinition = "LONGBLOB")
    private byte[] fotoPerfil;

    @Column(name = "ubicacion" ,nullable = false)
    private String ubicacion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "reputacion")
    private Double reputacion;

    @Column(name = "estado" ,nullable = false)
    private Boolean estado;

    @OneToMany(mappedBy = "usuario")
    private List<Oferta> ofertas;

    @OneToMany(mappedBy = "usuario")
    private List<Solicitud> solicitudes;

    @OneToMany(mappedBy = "usuarioOfertante")
    private List<Intercambio> intercambiosOfertas;

    @OneToMany(mappedBy = "usuarioSolicitante")
    private List<Intercambio> intercambiosSolicitante;

    @OneToMany(mappedBy = "usuarioCalificador")
    private List<Calificacion> calificacionesDadas;

    @OneToMany(mappedBy = "usuarioCalificado")
    private List<Calificacion> calificacionesRecibidas;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = LocalDateTime.now();
        this.reputacion = 0.00;
        this.estado = true;
    }
}
