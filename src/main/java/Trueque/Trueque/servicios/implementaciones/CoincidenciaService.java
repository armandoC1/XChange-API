package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.modelos.*;
import Trueque.Trueque.repositorios.CoincidenciaRepository;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.repositorios.ISolicitudRepository;
import Trueque.Trueque.servicios.interfaces.ICategoriaService;
import Trueque.Trueque.servicios.interfaces.ICoincidenciaService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

@Service
public class CoincidenciaService implements ICoincidenciaService {

    @Autowired
    private CoincidenciaRepository coincidenciaRepository;

    @Autowired
    private IOfertaRepository ofertaRepository;

    @Autowired
    private ISolicitudRepository solicitudRepository;


    // Implementación del método para crear una coincidencia
    @Override
    public Coincidencia crearCoincidencia(Long idOferta, Long idSolicitud) {
        // Buscar la oferta por ID
        Oferta oferta = ofertaRepository.findById(idOferta)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada con ID: " + idOferta));

        // Buscar la solicitud por ID
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + idSolicitud));

        // Crear una nueva coincidencia
        Coincidencia coincidencia = new Coincidencia();
        coincidencia.setOferta(oferta);
        coincidencia.setSolicitud(solicitud);

        // Guardar la coincidencia en la base de datos
        return coincidenciaRepository.save(coincidencia);
    }

    // Implementación del método para obtener una coincidencia por ID
    @Override
    public Coincidencia obtenerCoincidenciaPorId(Long idCoincidencia) {
        return coincidenciaRepository.findById(idCoincidencia)
                .orElseThrow(() -> new RuntimeException("Coincidencia no encontrada con ID: " + idCoincidencia));
    }
}
