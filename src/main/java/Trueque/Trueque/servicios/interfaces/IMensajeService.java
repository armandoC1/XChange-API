package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.mensaje.*;
import java.util.List;
import java.util.Map;

public interface IMensajeService {

    MensajeSalida guardarMensaje(MensajeGuardar mensajeGuardarDTO);
    List<MensajeSalida> obtenerMensajesPorOferta(Long idOferta);
    Map<Long, Map<Long, List<MensajeSalida>>> obtenerMensajesAgrupados( Long userId);
}
