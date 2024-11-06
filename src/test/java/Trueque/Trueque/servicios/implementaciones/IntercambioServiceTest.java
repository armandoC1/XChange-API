package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.intercambio.IntercambioGuardar;
import Trueque.Trueque.dtos.intercambio.IntercambioModificar;
import Trueque.Trueque.dtos.intercambio.IntercambioSalida;
import Trueque.Trueque.modelos.Intercambio;
import Trueque.Trueque.repositorios.IIntercambioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IntercambioServiceTest {

    @InjectMocks
    private IntercambioService intercambioService;

    @Mock
    private IIntercambioRepository intercambioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos() {
        Intercambio intercambio = new Intercambio();
        when(intercambioRepository.findAll()).thenReturn(Collections.singletonList(intercambio));

        List<IntercambioSalida> intercambios = intercambioService.obtenerTodos();

        assertNotNull(intercambios, "La lista de intercambios no debe ser nula");
        assertEquals(1, intercambios.size(), "La lista de intercambios debe tener un elemento");

        verify(intercambioRepository, times(1)).findAll();
    }

    @Test
    void obtenerTodosPaginados() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Intercambio> page = new PageImpl<>(Collections.singletonList(new Intercambio()));
        when(intercambioRepository.findAll(pageRequest)).thenReturn(page);

        var intercambios = intercambioService.obtenerTodoPaginados(pageRequest);

        assertNotNull(intercambios);
        assertEquals(1, intercambios.getSize(), "Debe haber al menos un intercambio en la página");
        verify(intercambioRepository, times(1)).findAll(pageRequest);
    }

    @Test
    void obtenerPorId() {
        Long idIntercambio = 1L;
        Intercambio intercambio = new Intercambio();
        when(intercambioRepository.findById(idIntercambio)).thenReturn(Optional.of(intercambio));

        IntercambioSalida resultado = intercambioService.obtenerPorId(idIntercambio);

        assertNotNull(resultado, "El intercambio obtenido no debe ser nulo");
        verify(intercambioRepository, times(1)).findById(idIntercambio);
    }

    @Test
    void crear() {
        IntercambioGuardar dto = new IntercambioGuardar(1L, 2L, 3L, 4L);

        Intercambio intercambio = new Intercambio();
        when(intercambioRepository.save(any(Intercambio.class))).thenReturn(intercambio);

        IntercambioSalida resultado = intercambioService.crear(dto);

        assertNotNull(resultado, "El intercambio creado no debe ser nulo");
        verify(intercambioRepository, times(1)).save(any(Intercambio.class));
    }


    @Test
    void eliminarPorId() {
        Long idIntercambio = 1L;
        doNothing().when(intercambioRepository).deleteById(idIntercambio);

        assertDoesNotThrow(() -> intercambioService.elimiarPorId(idIntercambio), "La eliminación no debe lanzar ninguna excepción");
        verify(intercambioRepository, times(1)).deleteById(idIntercambio);
    }
}
