package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.oferta.*;
import Trueque.Trueque.modelos.Categoria;
import Trueque.Trueque.modelos.Oferta;
import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.ICategoriaRepository;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.IOfertaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
    private IUsuarioRepository usuarioRepository;
    @Override
    public List<OfertaSalida> obtenerTodos() {
        List<Oferta> ofertas = ofertaRepository.findAll();
        return ofertas.stream()
                .map(oferta -> modelMapper.map(oferta , OfertaSalida.class))
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
    public OfertaSalida crear(OfertaGuardar ofertaGuardar) {
        // Crear una nueva instancia de Oferta sin usar ModelMapper para los campos conflictivos
        Oferta oferta = new Oferta();

        // Mapear las propiedades que no son relaciones (atributos simples)
        oferta.setTitulo(ofertaGuardar.getTitulo());
        oferta.setDescripcion(ofertaGuardar.getDescripcion());
        oferta.setCondicion(ofertaGuardar.getCondicion());
        oferta.setUbicacion(ofertaGuardar.getUbicacion());
        oferta.setImagenes(ofertaGuardar.getImagenes());

        // Cargar la entidad Categoria desde la base de datos
        Categoria categoria = categoriaRepository.findById(ofertaGuardar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));

        // Cargar la entidad Usuario desde la base de datos
        Usuario usuario = usuarioRepository.findById(ofertaGuardar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Asignar las entidades cargadas a la oferta
        oferta.setCategoria(categoria);
        oferta.setUsuario(usuario);

        // Guardar la oferta en la base de datos
        oferta = ofertaRepository.save(oferta);

        // Devolver la oferta guardada mapeada a DTO OfertaSalida
        return modelMapper.map(oferta, OfertaSalida.class);
    }

    @Override
    public List<OfertaSalida> buscarPorTitulo(String titulo) {
        List<Oferta> ofertas = ofertaRepository.findByTituloContainingIgnoreCase(titulo);
        // Mapear las entidades Oferta a OfertaSalida
        return ofertas.stream()
                .map(oferta -> modelMapper.map(oferta, OfertaSalida.class))
                .collect(Collectors.toList());
    }


    @Override
    public OfertaSalida editar(OfertaModificar ofertaModificar) {
        // Cargar la oferta existente desde la base de datos
        Oferta ofertaExistente = ofertaRepository.findById(ofertaModificar.getIdOferta())
                .orElseThrow(() -> new RuntimeException("La oferta con ID " + ofertaModificar.getIdOferta() + " no existe."));

        // Mapear solo los campos que se deben actualizar
        modelMapper.map(ofertaModificar, ofertaExistente);

        // Cargar el usuario y la categoría desde la base de datos
        Usuario usuario = usuarioRepository.findById(ofertaModificar.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("El usuario con ID " + ofertaModificar.getIdUsuario() + " no existe."));
        Categoria categoria = categoriaRepository.findById(ofertaModificar.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("La categoría con ID " + ofertaModificar.getIdCategoria() + " no existe."));

        // Asignar el usuario y la categoría a la oferta
        ofertaExistente.setUsuario(usuario);
        ofertaExistente.setCategoria(categoria);

        // Guardar la oferta actualizada
        ofertaExistente = ofertaRepository.save(ofertaExistente);

        // Retornar la oferta actualizada mapeada a un DTO de salida
        return modelMapper.map(ofertaExistente, OfertaSalida.class);
    }


    @Override
    public void eliminarPorId(Long idOferta) {
        ofertaRepository.deleteById(idOferta);
    }
}
