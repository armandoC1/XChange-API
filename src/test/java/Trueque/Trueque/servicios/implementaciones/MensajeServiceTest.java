package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.mensaje.MensajeGuardar;
import Trueque.Trueque.dtos.mensaje.MensajeSalida;
import Trueque.Trueque.modelos.Mensaje;
import Trueque.Trueque.modelos.Oferta;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.repositorios.IMensajeRepository;
import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.seguridad.repositorios.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MensajeServiceTest {

    @InjectMocks
    private MensajeService mensajeService;

    @Mock
    private IMensajeRepository mensajeRepository;

    @Mock
    private IOfertaRepository ofertaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarMensaje() {
        // Arrange
        MensajeGuardar mensajeGuardarDTO = new MensajeGuardar();
        mensajeGuardarDTO.setIdOferta(1L);
        mensajeGuardarDTO.setIdRemitente(2L);
        mensajeGuardarDTO.setIdDestinatario(3L);
        mensajeGuardarDTO.setContenidoMensaje("Hola, este es un mensaje de prueba.");

        Oferta oferta = new Oferta();
        Usuario remitente = new Usuario();
        Usuario destinatario = new Usuario();
        Mensaje mensaje = new Mensaje();
        MensajeSalida mensajeSalida = new MensajeSalida();

        when(ofertaRepository.findById(1L)).thenReturn(Optional.of(oferta));
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(remitente));
        when(usuarioRepository.findById(3L)).thenReturn(Optional.of(destinatario));
        when(mensajeRepository.save(any(Mensaje.class))).thenReturn(mensaje);
        when(modelMapper.map(mensaje, MensajeSalida.class)).thenReturn(mensajeSalida);

        // Act
        MensajeSalida resultado = mensajeService.guardarMensaje(mensajeGuardarDTO);

        // Assert
        assertNotNull(resultado, "El mensaje guardado no debe ser nulo");
        verify(ofertaRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).findById(2L);
        verify(usuarioRepository, times(1)).findById(3L);
        verify(mensajeRepository, times(1)).save(any(Mensaje.class));
        verify(modelMapper, times(1)).map(mensaje, MensajeSalida.class);
    }

    @Test
    void obtenerMensajesPorOferta() {
        // Arrange
        Long idOferta = 1L;
        Mensaje mensaje = new Mensaje();
        MensajeSalida mensajeSalida = new MensajeSalida();

        when(mensajeRepository.findByOfertaIdOferta(idOferta)).thenReturn(Collections.singletonList(mensaje));
        when(modelMapper.map(mensaje, MensajeSalida.class)).thenReturn(mensajeSalida);

        // Act
        List<MensajeSalida> mensajes = mensajeService.obtenerMensajesPorOferta(idOferta);

        // Assert
        assertNotNull(mensajes, "La lista de mensajes no debe ser nula");
        assertEquals(1, mensajes.size(), "La lista de mensajes debe tener un elemento");
        verify(mensajeRepository, times(1)).findByOfertaIdOferta(idOferta);
        verify(modelMapper, times(1)).map(mensaje, MensajeSalida.class);
    }

    @Test
    void obtenerMensajesEntreUsuarios() {
        // Arrange
        Long idOferta = 1L;
        Long idRemitente = 2L;
        Long idDestinatario = 3L;
        Mensaje mensaje = new Mensaje();
        MensajeSalida mensajeSalida = new MensajeSalida();

        when(mensajeRepository.findByOfertaIdOfertaAndRemitenteIdUsuarioAndDestinatarioIdUsuario(idOferta, idRemitente, idDestinatario))
                .thenReturn(Collections.singletonList(mensaje));
        when(modelMapper.map(mensaje, MensajeSalida.class)).thenReturn(mensajeSalida);

        // Act
        List<MensajeSalida> mensajes = mensajeService.obtenerMensajesEntreUsuarios(idOferta, idRemitente, idDestinatario);

        // Assert
        assertNotNull(mensajes, "La lista de mensajes no debe ser nula");
        assertEquals(1, mensajes.size(), "La lista de mensajes debe tener un elemento");
        verify(mensajeRepository, times(1)).findByOfertaIdOfertaAndRemitenteIdUsuarioAndDestinatarioIdUsuario(idOferta, idRemitente, idDestinatario);
        verify(modelMapper, times(1)).map(mensaje, MensajeSalida.class);
    }
}
