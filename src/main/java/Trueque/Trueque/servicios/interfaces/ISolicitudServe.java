package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.oferta.*;
import Trueque.Trueque.dtos.solicitud.SolicitudGuardar;
import Trueque.Trueque.dtos.solicitud.SolicitudModificar;
import Trueque.Trueque.dtos.solicitud.SolicitudSalida;
import org.springframework.data.domain.*;

import java.util.*;

public interface ISolicitudServe {

    List<SolicitudSalida> obtenerTodos();

    Page<SolicitudSalida> obtenerTodosPaginados(Pageable pageable);

    SolicitudSalida obtenerPorId (Long idSolicitud);

    SolicitudSalida crear (SolicitudGuardar solicitudGuardar);

    SolicitudSalida editar (SolicitudModificar solicitudModificar);

    void eliminarPorId (Long idSolicitud);

}
