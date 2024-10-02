package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.solicitud.*;
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
