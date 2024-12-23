package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.solicitud.SolicitudGuardar;
import Trueque.Trueque.dtos.solicitud.SolicitudModificar;
import Trueque.Trueque.dtos.solicitud.SolicitudSalida;
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
import java.util.ArrayList;

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
        return modelMapper.map(solicitudRepository.findById(idSolicitud).get(), SolicitudSalida.class);
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

        Usuario usuario = usuarioRepository.findById(solicitudGuardar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        solicitud.setCategoria(categoria);
        solicitud.setUsuario(usuario);

        solicitud = solicitudRepository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudSalida.class);
    }

    public SolicitudSalida editar(Long idSolicitud, SolicitudModificar solicitudModificar, List<MultipartFile> imagenes) throws IOException {
        Solicitud solicitudExistente = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("La solicitud con ID " + idSolicitud + " no existe."));

        solicitudExistente.setTitulo(solicitudModificar.getTitulo());
        solicitudExistente.setDescripcion(solicitudModificar.getDescripcion());
        solicitudExistente.setUbicacion(solicitudModificar.getUbicacion());

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

        Usuario usuario = usuarioRepository.findById(solicitudModificar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Categoria categoria = categoriaRepository.findById(solicitudModificar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        solicitudExistente.setUsuario(usuario);
        solicitudExistente.setCategoria(categoria);

        solicitudExistente = solicitudRepository.save(solicitudExistente);

        return modelMapper.map(solicitudExistente, SolicitudSalida.class);
    }

    @Override
    public void eliminarPorId(Long idSolicitud) {
        solicitudRepository.deleteById(idSolicitud);
    }
}
