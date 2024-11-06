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
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();

        when(solicitudRepository.findAll()).thenReturn(Collections.singletonList(solicitud));
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        List<SolicitudSalida> solicitudes = solicitudService.obtenerTodos();
        assertNotNull(solicitudes);
        assertEquals(1, solicitudes.size(), "La lista de solicitudes debe contener un elemento");
        verify(solicitudRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }

    @Test
    void obtenerTodosPaginados() {
        Pageable pageable = PageRequest.of(0, 10);
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();
        Page<Solicitud> solicitudPage = new PageImpl<>(Collections.singletonList(solicitud), pageable, 1);

        when(solicitudRepository.findAll(pageable)).thenReturn(solicitudPage);
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        Page<SolicitudSalida> solicitudes = solicitudService.obtenerTodosPaginados(pageable);

        assertNotNull(solicitudes);
        assertEquals(1, solicitudes.getTotalElements(), "La lista de solicitudes debe contener un elemento");
        verify(solicitudRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }

    @Test
    void obtenerPorId() {
        Long idSolicitud = 1L;
        Solicitud solicitud = new Solicitud();
        SolicitudSalida solicitudSalida = new SolicitudSalida();

        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.of(solicitud));
        when(modelMapper.map(solicitud, SolicitudSalida.class)).thenReturn(solicitudSalida);

        SolicitudSalida resultado = solicitudService.obtenerPorId(idSolicitud);

        assertNotNull(resultado);
        verify(solicitudRepository, times(1)).findById(idSolicitud);
        verify(modelMapper, times(1)).map(solicitud, SolicitudSalida.class);
    }


    @Test
    void eliminarPorId() {
        Long idSolicitud = 1L;

        solicitudService.eliminarPorId(idSolicitud);

        verify(solicitudRepository, times(1)).deleteById(idSolicitud);
    }
}
