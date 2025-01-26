package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.dtos.oferta.*;
import Trueque.Trueque.modelos.*;
import Trueque.Trueque.repositorios.*;
import Trueque.Trueque.seguridad.repositorios.UsuarioRepository;
import Trueque.Trueque.servicios.interfaces.IOfertaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfertaService implements IOfertaService {

    @Autowired
    private IOfertaRepository ofertaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<OfertaSalida> obtenerTodos() {
        List<Oferta> ofertas = ofertaRepository.findAll();
        return ofertas.stream()
                .map(oferta -> {
                    OfertaSalida dto = modelMapper.map(oferta, OfertaSalida.class);

                    List<byte[]> imagenesDTO = new ArrayList<>();

                    for (byte[] imagen : oferta.getImagenes()) {
                        imagenesDTO.add(imagen);
                    }

                    dto.setImagenes(imagenesDTO);

                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<OfertaSalida> obtenerTodoPaginados(Pageable pageable) {
        Page<Oferta> page = ofertaRepository.findAll(pageable);

        List<OfertaSalida> ofertaDto = page.stream()
                .map(oferta -> modelMapper.map(oferta, OfertaSalida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(ofertaDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public OfertaSalida obtenerPorId(Long idOferta) {
        return modelMapper.map(ofertaRepository.findById(idOferta).get(), OfertaSalida.class);
    }


@Override
public OfertaSalida crear(OfertaGuardar ofertaGuardar, List<MultipartFile> imagenes) throws IOException  {

    Oferta oferta = new Oferta();

    oferta.setTitulo(ofertaGuardar.getTitulo());
    oferta.setDescripcion(ofertaGuardar.getDescripcion());
    oferta.setCondicion(ofertaGuardar.getCondicion());
    oferta.setUbicacion(ofertaGuardar.getUbicacion());

    List<byte[]> imagenesGuardar = imagenes.stream().
            map(imagen -> {
                try{
                    return imagen.getBytes();
                }
                catch (IOException exception){
                    throw new RuntimeException("Error al obtener las imagenes");
                }
            })
            .collect(Collectors.toList());
    oferta.setImagenes(imagenesGuardar);

    Categoria categoria = categoriaRepository.findById(ofertaGuardar.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

    Usuario usuario = usuarioRepository.findById(ofertaGuardar.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    oferta.setCategoria(categoria);
    oferta.setUsuario(usuario);

    oferta = ofertaRepository.save(oferta);

    return modelMapper.map(oferta, OfertaSalida.class);
}
    @Override
    public List<OfertaSalida> buscarPorTitulo(String titulo) {
        List<Oferta> ofertas = ofertaRepository.findByTituloContainingIgnoreCase(titulo);

        return ofertas.stream()
                .map(oferta -> modelMapper.map(oferta, OfertaSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public OfertaSalida editar(Long idOferta, OfertaModificar ofertaModificar, List<MultipartFile> imagenes) throws IOException {

        Oferta ofertaExistente = ofertaRepository.findById(idOferta)
                .orElseThrow(() -> new RuntimeException("La oferta con ID " + idOferta + " no existe."));

        ofertaExistente.setTitulo(ofertaModificar.getTitulo());
        ofertaExistente.setDescripcion(ofertaModificar.getDescripcion());
        ofertaExistente.setCondicion(ofertaModificar.getCondicion());
        ofertaExistente.setUbicacion(ofertaModificar.getUbicacion());
        ofertaExistente.setEstado(ofertaModificar.getEstado());

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
            ofertaExistente.setImagenes(imagenesBytes);
        }

        Usuario usuario = usuarioRepository.findById(ofertaModificar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + ofertaModificar.getIdUsuario() + " no existe."));
        Categoria categoria = categoriaRepository.findById(ofertaModificar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("La categoría con ID " + ofertaModificar.getIdCategoria() + " no existe."));

        ofertaExistente.setUsuario(usuario);
        ofertaExistente.setCategoria(categoria);

        ofertaExistente = ofertaRepository.save(ofertaExistente);

        return modelMapper.map(ofertaExistente, OfertaSalida.class);
    }

    @Override
    public void eliminarPorId(Long idOferta) {
        ofertaRepository.deleteById(idOferta);
    }

    @Override
    public List<OfertaSalida> obtenerOfertasPorUsuario(Long userId) {
        List<Oferta> ofertas = ofertaRepository.findOfertasByUsuarioId(userId);
        return ofertas.stream()
                .map(oferta -> modelMapper.map(oferta, OfertaSalida.class))
                .collect(Collectors.toList());
    }
}
