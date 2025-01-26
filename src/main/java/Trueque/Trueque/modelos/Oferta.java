package Trueque.Trueque.modelos;
import Trueque.Trueque.seguridad.modelos.Usuario;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ofertas")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta")
    private Long idOferta;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "condicion", nullable = false)
    private String condicion;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "imagenes", columnDefinition = "LONGBLOB")
    private  List<byte[]> imagenes;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "estado")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "oferta")
    private List<Coincidencia> coincidencias;

    @OneToMany(mappedBy = "oferta")
    private List<Intercambio> intercambios;

    @PrePersist
    protected void onCreate(){
        this.fechaCreacion = ZonedDateTime.now(ZoneId.of("America/El_Salvador")).toLocalDateTime();
        this.estado = "activa";
    }

}
