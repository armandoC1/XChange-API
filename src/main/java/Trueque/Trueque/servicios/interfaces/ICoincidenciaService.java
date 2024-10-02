package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.modelos.Coincidencia;

public interface ICoincidenciaService {

    Coincidencia crearCoincidencia(Long idOferta, Long idSolicitud);

    Coincidencia obtenerCoincidenciaPorId(Long idCoincidencia);

}


