package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.solicitud.SolicitudGuardar;
import Trueque.Trueque.dtos.solicitud.SolicitudModificar;
import Trueque.Trueque.dtos.solicitud.SolicitudSalida;
import Trueque.Trueque.dtos.usuario.*;
import Trueque.Trueque.modelos.Categoria;
import Trueque.Trueque.modelos.Solicitud;
import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.ICategoriaRepository;
import Trueque.Trueque.repositorios.ISolicitudRepository;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.ISolicitudServe;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SolicitudService implements ISolicitudServe {
    @Autowired
    private ISolicitudRepository solicitudRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IUsuarioRepository usuarioRepository;

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
                .map(solicitud -> modelMapper.map(solicitud , SolicitudSalida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(solicitudDto, page.getPageable() , page.getTotalElements());
    }

    @Override
    public SolicitudSalida obtenerPorId(Long idSolicitud) {
        return modelMapper.map(solicitudRepository.findById(idSolicitud).get(), SolicitudSalida.class);
    }

    @Override
    public SolicitudSalida crear(SolicitudGuardar solicitudGuardar) {
        Solicitud solicitud = new Solicitud();

        solicitud.setTitulo(solicitudGuardar.getTitulo());
        solicitud.setDescripcion(solicitudGuardar.getDescripcion());
        solicitud.setUbicacion(solicitudGuardar.getUbicacion());

        Categoria categoria = categoriaRepository.findById(solicitudGuardar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        Usuario usuario = usuarioRepository.findById(solicitudGuardar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        solicitud.setCategoria(categoria);
        solicitud.setUsuario(usuario);

        solicitud = solicitudRepository.save(solicitud);
        return modelMapper.map(solicitud, SolicitudSalida.class);
    }

    @Override
    public SolicitudSalida editar(SolicitudModificar solicitudModificar) {

        Solicitud solicitudExistente = solicitudRepository.findById(solicitudModificar.getIdSolicitud())
                .orElseThrow(() -> new RuntimeException("La solicitud con id"+ solicitudModificar.getIdSolicitud() + "no existe"));

        modelMapper.map(solicitudModificar, solicitudExistente);

        Usuario usuario = usuarioRepository.findById(solicitudModificar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + solicitudModificar.getIdUsuario() + " no existe."));
        Categoria categoria = categoriaRepository.findById(solicitudModificar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("La categoría con ID " + solicitudModificar.getIdCategoria() + " no existe."));

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
