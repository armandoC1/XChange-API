package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.modelos.Coincidencia;

public interface ICoincidenciaService {

    // Método para crear una coincidencia entre una oferta y una solicitud
    Coincidencia crearCoincidencia(Long idOferta, Long idSolicitud);

    // Método para obtener una coincidencia por su ID
    Coincidencia obtenerCoincidenciaPorId(Long idCoincidencia);

    // Otros métodos relacionados con la coincidencia (si los necesitas)
}


