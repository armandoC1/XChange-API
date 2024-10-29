package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.oferta.OfertaGuardar;
import Trueque.Trueque.dtos.oferta.OfertaModificar;
import Trueque.Trueque.dtos.oferta.OfertaSalida;
import Trueque.Trueque.modelos.Categoria;
import Trueque.Trueque.modelos.Oferta;
import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.repositorios.ICategoriaRepository;
import Trueque.Trueque.repositorios.IOfertaRepository;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OfertaServiceTest {

    @InjectMocks
    private OfertaService ofertaService;

    @Mock
    private IOfertaRepository ofertaRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private ICategoriaRepository categoriaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerTodos() {
        // Arrange
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();

        when(ofertaRepository.findAll()).thenReturn(Collections.singletonList(oferta));
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        // Act
        List<OfertaSalida> ofertas = ofertaService.obtenerTodos();

        // Assert
        assertNotNull(ofertas);
        assertEquals(1, ofertas.size(), "La lista de ofertas debe contener un elemento");
        verify(ofertaRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void obtenerTodoPaginados() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();
        Page<Oferta> ofertaPage = new PageImpl<>(Collections.singletonList(oferta), pageable, 1);

        when(ofertaRepository.findAll(pageable)).thenReturn(ofertaPage);
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        // Act
        Page<OfertaSalida> ofertas = ofertaService.obtenerTodoPaginados(pageable);

        // Assert
        assertNotNull(ofertas);
        assertEquals(1, ofertas.getTotalElements(), "La lista de ofertas debe contener un elemento");
        verify(ofertaRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void obtenerPorId() {
        // Arrange
        Long idOferta = 1L;
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();

        when(ofertaRepository.findById(idOferta)).thenReturn(Optional.of(oferta));
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        // Act
        OfertaSalida resultado = ofertaService.obtenerPorId(idOferta);

        // Assert
        assertNotNull(resultado);
        verify(ofertaRepository, times(1)).findById(idOferta);
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void crear() throws IOException {
        // Arrange
        OfertaGuardar ofertaGuardar = new OfertaGuardar();
        MultipartFile imagenMock = mock(MultipartFile.class);
        List<MultipartFile> imagenes = List.of(imagenMock);
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();
        Categoria categoria = new Categoria();
        Usuario usuario = new Usuario();

        when(imagenMock.getBytes()).thenReturn(new byte[1]);
        when(categoriaRepository.findById(ofertaGuardar.getIdCategoria())).thenReturn(Optional.of(categoria));
        when(usuarioRepository.findById(ofertaGuardar.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(ofertaRepository.save(any(Oferta.class))).thenReturn(oferta);
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        // Act
        OfertaSalida resultado = ofertaService.crear(ofertaGuardar, imagenes);

        // Assert
        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).findById(ofertaGuardar.getIdCategoria());
        verify(usuarioRepository, times(1)).findById(ofertaGuardar.getIdUsuario());
        verify(ofertaRepository, times(1)).save(any(Oferta.class));
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void buscarPorTitulo() {
        // Arrange
        String titulo = "prueba";
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();

        when(ofertaRepository.findByTituloContainingIgnoreCase(titulo)).thenReturn(Collections.singletonList(oferta));
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        // Act
        List<OfertaSalida> ofertas = ofertaService.buscarPorTitulo(titulo);

        // Assert
        assertNotNull(ofertas);
        assertEquals(1, ofertas.size(), "La lista de ofertas debe contener un elemento");
        verify(ofertaRepository, times(1)).findByTituloContainingIgnoreCase(titulo);
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void editar() {
        // Arrange
        OfertaModificar ofertaModificar = new OfertaModificar();
        ofertaModificar.setIdOferta(1L);
        Oferta ofertaExistente = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();
        Categoria categoria = new Categoria();
        Usuario usuario = new Usuario();

        when(ofertaRepository.findById(ofertaModificar.getIdOferta())).thenReturn(Optional.of(ofertaExistente));
        when(usuarioRepository.findById(ofertaModificar.getIdUsuario())).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(ofertaModificar.getIdCategoria())).thenReturn(Optional.of(categoria));
        when(ofertaRepository.save(ofertaExistente)).thenReturn(ofertaExistente);
        when(modelMapper.map(ofertaExistente, OfertaSalida.class)).thenReturn(ofertaSalida);

        // Act
        OfertaSalida resultado = ofertaService.editar(ofertaModificar);

        // Assert
        assertNotNull(resultado);
        verify(ofertaRepository, times(1)).findById(ofertaModificar.getIdOferta());
        verify(usuarioRepository, times(1)).findById(ofertaModificar.getIdUsuario());
        verify(categoriaRepository, times(1)).findById(ofertaModificar.getIdCategoria());
        verify(ofertaRepository, times(1)).save(ofertaExistente);
        verify(modelMapper, times(1)).map(ofertaExistente, OfertaSalida.class);
    }

    @Test
    void eliminarPorId() {
        // Arrange
        Long idOferta = 1L;

        // Act
        ofertaService.eliminarPorId(idOferta);

        // Assert
        verify(ofertaRepository, times(1)).deleteById(idOferta);
    }
}
