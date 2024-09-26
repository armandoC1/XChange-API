package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.mensaje.*;
import java.util.*;

public interface IMensajeService {

    MensajeSalida guardarMensaje(MensajeGuardar mensajeGuardar);  // Método para guardar un mensaje

    MensajeSalida modificarMensaje(MensajeModificar mensajeModificar);  // Método para modificar un mensaje

    List<MensajeSalida> obtenerMensajesPorCoincidencia(Long idCoincidencia);  // Obtener mensajes por coincidencia

    List<MensajeSalida> obtenerMensajesEnviadosPorUsuario(Long idRemitente);  // Obtener mensajes enviados por un usuario

    List<MensajeSalida> obtenerMensajesRecibidosPorUsuario(Long idDestinatario);  // Obtener mensajes recibidos por un usuario
}
