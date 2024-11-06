package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.solicitud.*;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface ISolicitudServe {

    List<SolicitudSalida> obtenerTodos();

    Page<SolicitudSalida> obtenerTodosPaginados(Pageable pageable);

    SolicitudSalida obtenerPorId(Long idSolicitud);

    SolicitudSalida crear(SolicitudGuardar solicitudGuardar, List<MultipartFile> imagenes) throws IOException;

    SolicitudSalida editar(Long idSolicitud, SolicitudModificar solicitudModificar, List<MultipartFile> imagenes) throws IOException;

    void eliminarPorId(Long idSolicitud);

}
