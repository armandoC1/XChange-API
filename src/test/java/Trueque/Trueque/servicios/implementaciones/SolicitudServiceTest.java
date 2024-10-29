package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.solicitud.SolicitudGuardar;
import Trueque.Trueque.dtos.solicitud.SolicitudModificar;
import Trueque.Trueque.dtos.solicitud.SolicitudSalida;
import Trueque.Trueque.modelos.Categoria;
import Trueque.Trueque.modelos.Solicitud;
import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.repositorios.ICategoriaRepository;
import Trueque.Trueque.repositorios.ISolicitudRepository;
import Trueque.Trueque.seguridad.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolicitudServiceTest {

    @InjectMocks
    private SolicitudService solicitudService;

    @Mock
    private ISolicitudRepository solicitudRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ICategoriaRepository categoriaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();

        when(solicitudRepository.findAll()).thenReturn(Collections.singletonList(solicitud));
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        // Act
        List<SolicitudSalida> solicitudes = solicitudService.obtenerTodos();

        // Assert
        assertNotNull(solicitudes);
        assertEquals(1, solicitudes.size(), "La lista de solicitudes debe contener un elemento");
        verify(solicitudRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }

    @Test
    void obtenerTodosPaginados() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();
        Page<Solicitud> solicitudPage = new PageImpl<>(Collections.singletonList(solicitud), pageable, 1);

        when(solicitudRepository.findAll(pageable)).thenReturn(solicitudPage);
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        // Act
        Page<SolicitudSalida> solicitudes = solicitudService.obtenerTodosPaginados(pageable);

        // Assert
        assertNotNull(solicitudes);
        assertEquals(1, solicitudes.getTotalElements(), "La lista de solicitudes debe contener un elemento");
        verify(solicitudRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }

    @Test
    void obtenerPorId() {
        // Arrange
        Long idSolicitud = 1L;
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        // Act
        SolicitudSalida resultado = solicitudService.obtenerPorId(idSolicitud);

        // Assert
        assertNotNull(resultado);
        verify(solicitudRepository, times(1)).findById(idSolicitud);
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }

    @Test
    void crear() {
        // Arrange
        SolicitudGuardar solicitudGuardar = new SolicitudGuardar();
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();
        Categoria categoria = new Categoria();
        Usuario usuario = new Usuario();

        when(categoriaRepository.findById(solicitudGuardar.getIdCategoria())).thenReturn(Optional.of(categoria));
        when(usuarioRepository.findById(solicitudGuardar.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitud);
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        // Act
        SolicitudSalida resultado = solicitudService.crear(solicitudGuardar);

        // Assert
        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).findById(solicitudGuardar.getIdCategoria());
        verify(usuarioRepository, times(1)).findById(solicitudGuardar.getIdUsuario());
        verify(solicitudRepository, times(1)).save(any(Solicitud.class));
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }

    @Test
    void editar() {
        // Arrange
        SolicitudModificar solicitudModificar = new SolicitudModificar();
        solicitudModificar.setIdSolicitud(1L);
        Solicitud solicitudExistente = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();
        Categoria categoria = new Categoria();
        Usuario usuario = new Usuario();

        when(solicitudRepository.findById(solicitudModificar.getIdSolicitud())).thenReturn(Optional.of(solicitudExistente));
        when(usuarioRepository.findById(solicitudModificar.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(solicitudModificar.getIdCategoria())).thenReturn(Optional.of(categoria));
        when(solicitudRepository.save(solicitudExistente)).thenReturn(solicitudExistente);
        when(modelMapper.map(solicitudExistente, SolicitudSalida.class)).thenReturn(solicitudSalida);

        // Act
        SolicitudSalida resultado = solicitudService.editar(solicitudModificar);

        // Assert
        assertNotNull(resultado);
        verify(solicitudRepository, times(1)).findById(solicitudModificar.getIdSolicitud());
        verify(usuarioRepository, times(1)).findById(solicitudModificar.getIdUsuario());
        verify(categoriaRepository, times(1)).findById(solicitudModificar.getIdCategoria());
        verify(solicitudRepository, times(1)).save(solicitudExistente);
        verify(modelMapper, times(1)).map(solicitudExistente, SolicitudSalida.class);
    }

    @Test
    void eliminarPorId() {
        // Arrange
        Long idSolicitud = 1L;

        // Act
        solicitudService.eliminarPorId(idSolicitud);

        // Assert
        verify(solicitudRepository, times(1)).deleteById(idSolicitud);
    }
}
