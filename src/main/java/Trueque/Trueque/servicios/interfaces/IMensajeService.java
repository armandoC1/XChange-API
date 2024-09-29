package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.mensaje.*;
import java.util.List;

public interface IMensajeService {

    MensajeSalida guardarMensaje(MensajeGuardar mensajeGuardarDTO);
    List<MensajeSalida> obtenerMensajesPorOferta(Long idOferta);
    List<MensajeSalida> obtenerMensajesEntreUsuarios(Long idOferta, Long idRemitente, Long idDestinatario);
}
