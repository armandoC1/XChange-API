package Trueque.Trueque.seguridad.modelos;

import Trueque.Trueque.modelos.Calificacion;
import Trueque.Trueque.modelos.Intercambio;
import Trueque.Trueque.modelos.Oferta;
import Trueque.Trueque.modelos.Solicitud;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "correo_electronico"))
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre_usuario" ,nullable = false)
    private String nombre;


    @Column(name = "correo_electronico" ,nullable = false ,unique = true)
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

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Rol rol;

    @Column(name = "reputacion")
    private Double reputacion;

    @Column(name = "estado")
    private Boolean estado;

    @OneToMany(mappedBy = "usuario")
    private List<Oferta> ofertas;

    // Lista de solicitudes donde el usuario es el solicitante
    @OneToMany(mappedBy = "solicitante")
    private List<Solicitud> solicitudesSolicitadas;

    // Lista de solicitudes donde el usuario es el destinatario
    @OneToMany(mappedBy = "destinatario")
    private List<Solicitud> solicitudesRecibidas;

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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((rol.getNombre())));
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return correo;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}