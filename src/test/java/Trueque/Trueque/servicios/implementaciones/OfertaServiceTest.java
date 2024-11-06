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

        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();

        when(ofertaRepository.findAll()).thenReturn(Collections.singletonList(oferta));
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        List<OfertaSalida> ofertas = ofertaService.obtenerTodos();

        assertNotNull(ofertas);
        assertEquals(1, ofertas.size(), "La lista de ofertas debe contener un elemento");
        verify(ofertaRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void obtenerTodoPaginados() {

        Pageable pageable = PageRequest.of(0, 10);
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();
        Page<Oferta> ofertaPage = new PageImpl<>(Collections.singletonList(oferta), pageable, 1);

        when(ofertaRepository.findAll(pageable)).thenReturn(ofertaPage);
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        Page<OfertaSalida> ofertas = ofertaService.obtenerTodoPaginados(pageable);

        assertNotNull(ofertas);
        assertEquals(1, ofertas.getTotalElements(), "La lista de ofertas debe contener un elemento");
        verify(ofertaRepository, times(1)).findAll(pageable);
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void obtenerPorId() {

        Long idOferta = 1L;
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();

        when(ofertaRepository.findById(idOferta)).thenReturn(Optional.of(oferta));
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        OfertaSalida resultado = ofertaService.obtenerPorId(idOferta);

        assertNotNull(resultado);
        verify(ofertaRepository, times(1)).findById(idOferta);
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void crear() throws IOException {

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

        OfertaSalida resultado = ofertaService.crear(ofertaGuardar, imagenes);

        assertNotNull(resultado);
        verify(categoriaRepository, times(1)).findById(ofertaGuardar.getIdCategoria());
        verify(usuarioRepository, times(1)).findById(ofertaGuardar.getIdUsuario());
        verify(ofertaRepository, times(1)).save(any(Oferta.class));
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }

    @Test
    void buscarPorTitulo() {

        String titulo = "prueba";
        Oferta oferta = new Oferta();
        OfertaSalida ofertaSalida = new OfertaSalida();

        when(ofertaRepository.findByTituloContainingIgnoreCase(titulo)).thenReturn(Collections.singletonList(oferta));
        when(modelMapper.map(oferta, OfertaSalida.class)).thenReturn(ofertaSalida);

        List<OfertaSalida> ofertas = ofertaService.buscarPorTitulo(titulo);

        assertNotNull(ofertas);
        assertEquals(1, ofertas.size(), "La lista de ofertas debe contener un elemento");
        verify(ofertaRepository, times(1)).findByTituloContainingIgnoreCase(titulo);
        verify(modelMapper, times(1)).map(oferta, OfertaSalida.class);
    }


    @Test
    void eliminarPorId() {

        Long idOferta = 1L;

        ofertaService.eliminarPorId(idOferta);

        verify(ofertaRepository, times(1)).deleteById(idOferta);
    }
}
