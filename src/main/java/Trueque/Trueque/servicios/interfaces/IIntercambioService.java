package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.intercambio.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface IIntercambioService {

    List<IntercambioSalida> obtenerTodos();

    Page<IntercambioSalida> obtenerTodoPaginados(Pageable pageable);

    IntercambioSalida obtenerPorId (Long intercambioId);

    IntercambioSalida crear (IntercambioGuardar intercambioGuardar);

    IntercambioSalida editar (IntercambioModificar intercambioModificar);

    void elimiarPorId(Long intercambioId);

}
