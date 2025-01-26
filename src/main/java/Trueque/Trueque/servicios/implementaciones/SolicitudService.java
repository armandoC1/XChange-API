package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.solicitud.*;
import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.modelos.Categoria;
import Trueque.Trueque.modelos.Solicitud;
import Trueque.Trueque.repositorios.*;
import Trueque.Trueque.seguridad.repositorios.UsuarioRepository;
import Trueque.Trueque.servicios.interfaces.ISolicitudServe;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService implements ISolicitudServe {

    @Autowired
    private ISolicitudRepository solicitudRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Override
    public List<SolicitudSalida> obtenerTodos() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();
        return solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<SolicitudSalida> obtenerTodosPaginados(Pageable pageable) {
        Page<Solicitud> page = solicitudRepository.findAll(pageable);
        List<SolicitudSalida> solicitudDto = page.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudSalida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(solicitudDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public SolicitudSalida obtenerPorId(Long idSolicitud) {
        return modelMapper.map(solicitudRepository.findById(idSolicitud).orElseThrow(
                () -> new RuntimeException("Solicitud no encontrada")), SolicitudSalida.class);
    }

    @Override
    public SolicitudSalida crear(SolicitudGuardar solicitudGuardar, List<MultipartFile> imagenes) throws IOException {
        Solicitud solicitud = new Solicitud();

        solicitud.setTitulo(solicitudGuardar.getTitulo());
        solicitud.setDescripcion(solicitudGuardar.getDescripcion());
        solicitud.setUbicacion(solicitudGuardar.getUbicacion());

        List<byte[]> imagenesGuardar = imagenes.stream()
                .map(imagen -> {
                    try {
                        return imagen.getBytes();
                    } catch (IOException exception) {
                        throw new RuntimeException("Error al obtener las imágenes");
                    }
                })
                .collect(Collectors.toList());
        solicitud.setImagenes(imagenesGuardar);

        Categoria categoria = categoriaRepository.findById(solicitudGuardar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Usuario solicitante = usuarioRepository.findById(solicitudGuardar.getIdSolicitante())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Usuario destinatario = usuarioRepository.findById(solicitudGuardar.getIdDestinatario())
                .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        solicitud.setCategoria(categoria);
        solicitud.setSolicitante(solicitante);
        solicitud.setDestinatario(destinatario);

        solicitud = solicitudRepository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudSalida.class);
    }

    @Override
    public void aceptarSolicitud(Long idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        // Cambia el estado de la solicitud a 'aceptada' o 'inactiva'
        solicitud.setEstado("aceptada");

        // Guarda el cambio en la base de datos
        solicitudRepository.save(solicitud);
    }


    @Override
    public SolicitudSalida editar(Long idSolicitud, SolicitudModificar solicitudModificar, List<MultipartFile> imagenes) throws IOException {

        Solicitud solicitudExistente = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("La solicitud con ID " + idSolicitud + " no existe."));

        solicitudExistente.setTitulo(solicitudModificar.getTitulo());
        solicitudExistente.setDescripcion(solicitudModificar.getDescripcion());
        solicitudExistente.setUbicacion(solicitudModificar.getUbicacion());
        solicitudExistente.setEstado(solicitudModificar.getEstado());

        if (imagenes != null && !imagenes.isEmpty()) {
            List<byte[]> imagenesBytes = imagenes.stream()
                    .map(imagen -> {
                        try {
                            return imagen.getBytes();
                        } catch (IOException e) {
                            throw new RuntimeException("Error al procesar las imágenes", e);
                        }
                    })
                    .collect(Collectors.toList());
            solicitudExistente.setImagenes(imagenesBytes);
        }

        Usuario solicitante = usuarioRepository.findById(solicitudModificar.getIdSolicitante())
                .orElseThrow(() -> new RuntimeException("Usuario solicitante no encontrado"));
        solicitudExistente.setSolicitante(solicitante);

        Usuario destinatario = usuarioRepository.findById(solicitudModificar.getIdDestinatario())
                .orElseThrow(() -> new RuntimeException("Usuario destinatario no encontrado"));
        solicitudExistente.setDestinatario(destinatario);

        Categoria categoria = categoriaRepository.findById(solicitudModificar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        solicitudExistente.setCategoria(categoria);

        solicitudExistente = solicitudRepository.save(solicitudExistente);

        return modelMapper.map(solicitudExistente, SolicitudSalida.class);
    }


    @Override
    public void eliminarPorId(Long idSolicitud) {
        solicitudRepository.deleteById(idSolicitud);
    }

    @Override
    public List<SolicitudSalida> obtenerSolicitudesPorDestinatario(Long idDestinatario) {
        List<Solicitud> solicitudes = solicitudRepository.findByDestinatarioIdUsuario(idDestinatario);
        return solicitudes.stream()
                .map(solicitud -> modelMapper.map(solicitud, SolicitudSalida.class))
                .collect(Collectors.toList());
    }
}
