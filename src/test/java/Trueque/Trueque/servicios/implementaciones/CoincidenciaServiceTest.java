package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.modelos.Coincidencia;
import Trueque.Trueque.modelos.Oferta;
import Trueque.Trueque.modelos.Solicitud;
import Trueque.Trueque.repositorios.CoincidenciaRepository;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.repositorios.ISolicitudRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CoincidenciaServiceTest {

    @InjectMocks
    private CoincidenciaService coincidenciaService;

    @Mock
    private CoincidenciaRepository coincidenciaRepository;

    @Mock
    private IOfertaRepository ofertaRepository;

    @Mock
    private ISolicitudRepository solicitudRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    //test passed
    @Test
    void crearCoincidencia() {

        Long idOferta = 1L;
        Long idSolicitud = 2L;
        Oferta oferta = new Oferta();
        Solicitud solicitud = new Solicitud();
        Coincidencia coincidencia = new Coincidencia();

        when(ofertaRepository.findById(idOferta)).thenReturn(Optional.of(oferta));
        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.of(solicitud));
        when(coincidenciaRepository.save(any(Coincidencia.class))).thenReturn(coincidencia);

        Coincidencia resultado = coincidenciaService.crearCoincidencia(idOferta, idSolicitud);

        assertNotNull(resultado, "La coincidencia creada no debe ser nula");
        verify(coincidenciaRepository, times(1)).save(any(Coincidencia.class));
    }

    @Test
    void crearCoincidencia_OfertaNoEncontrada() {

        Long idOferta = 1L;
        Long idSolicitud = 2L;

        when(ofertaRepository.findById(idOferta)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            coincidenciaService.crearCoincidencia(idOferta, idSolicitud);
        });
        assertEquals("Oferta no encontrada con ID: " + idOferta, exception.getMessage());
    }

    @Test
    void crearCoincidencia_SolicitudNoEncontrada() {
        Long idOferta = 1L;
        Long idSolicitud = 2L;
        Oferta oferta = new Oferta();

        when(ofertaRepository.findById(idOferta)).thenReturn(Optional.of(oferta));
        when(solicitudRepository.findById(idSolicitud)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            coincidenciaService.crearCoincidencia(idOferta, idSolicitud);
        });
        assertEquals("Solicitud no encontrada con ID: " + idSolicitud, exception.getMessage());
    }

    @Test
    void obtenerCoincidenciaPorId() {

        Long idCoincidencia = 1L;
        Coincidencia coincidencia = new Coincidencia();

        when(coincidenciaRepository.findById(idCoincidencia)).thenReturn(Optional.of(coincidencia));

        Coincidencia resultado = coincidenciaService.obtenerCoincidenciaPorId(idCoincidencia);

        assertNotNull(resultado, "La coincidencia obtenida no debe ser nula");
    }

    @Test
    void obtenerCoincidenciaPorId_NoEncontrada() {
        Long idCoincidencia = 1L;

        when(coincidenciaRepository.findById(idCoincidencia)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            coincidenciaService.obtenerCoincidenciaPorId(idCoincidencia);
        });
        assertEquals("Coincidencia no encontrada con ID: " + idCoincidencia, exception.getMessage());
    }
}
