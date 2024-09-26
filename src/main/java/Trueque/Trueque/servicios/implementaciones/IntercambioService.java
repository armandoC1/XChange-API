package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.intercambio.IntercambioGuardar;
import Trueque.Trueque.dtos.intercambio.IntercambioModificar;
import Trueque.Trueque.dtos.intercambio.IntercambioSalida;
import Trueque.Trueque.modelos.Intercambio;
import Trueque.Trueque.modelos.Oferta;
import Trueque.Trueque.modelos.Solicitud;
import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.IIntercambioRepository;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.repositorios.ISolicitudRepository;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.IIntercambioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class IntercambioService implements IIntercambioService {

    @Autowired
    private IIntercambioRepository intercambioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IOfertaRepository ofertaRepository;

    @Autowired
    private ISolicitudRepository solicitudRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public List<IntercambioSalida> obtenerTodos() {
        List<Intercambio> intercambios = intercambioRepository.findAll();
        return intercambios.stream()
                .map(intercambio -> modelMapper.map(intercambio, IntercambioSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<IntercambioSalida> obtenerTodoPaginados(Pageable pageable) {

        Page<Intercambio> intercambiosPage = intercambioRepository.findAll(pageable);

        return intercambiosPage.map(intercambio -> modelMapper.map(intercambio, IntercambioSalida.class));
    }

    @Override
    public IntercambioSalida obtenerPorId(Long intercambioId) {

        Intercambio intercambio = intercambioRepository.findById(intercambioId)
                .orElseThrow(() -> new RuntimeException("Intercambio no encontrado con el ID: " + intercambioId));

        return modelMapper.map(intercambio, IntercambioSalida.class);
    }

    @Override
    @Transactional
    public IntercambioSalida crear(IntercambioGuardar intercambioGuardar) {

        Oferta oferta = ofertaRepository.findById(intercambioGuardar.getIdOferta())
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        Solicitud solicitud = solicitudRepository.findById(intercambioGuardar.getIdSolicitud())
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        Usuario usuarioOfertante = usuarioRepository.findById(intercambioGuardar.getIdUsurioOfertante())
                .orElseThrow(() -> new RuntimeException("Usuario ofertante no encontrado"));

        Usuario usuarioSolicitante = usuarioRepository.findById(intercambioGuardar.getIdUsuarioSolicitante())
                .orElseThrow(() -> new RuntimeException("Usuario solicitante no encontrado"));

        oferta.setEstado("completado");
        ofertaRepository.save(oferta);

        solicitud.setEstado("inactivo");
        solicitudRepository.save(solicitud);

        Intercambio intercambio = new Intercambio();

        intercambio.setOferta(oferta);
        intercambio.setSolicitud(solicitud);
        intercambio.setUsuarioOfertante(usuarioOfertante);
        intercambio.setUsuarioSolicitante(usuarioSolicitante);

        Intercambio intercambioGuardado = intercambioRepository.save(intercambio);

        return modelMapper.map(intercambioGuardar, IntercambioSalida.class);
    }

    @Override
    public IntercambioSalida editar(IntercambioModificar intercambioModificar) {
        return null;
    }

    @Override
    public void elimiarPorId(Long intercambioId) {

    }
}
