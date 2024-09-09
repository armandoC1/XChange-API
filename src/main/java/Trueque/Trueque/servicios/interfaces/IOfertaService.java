package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.oferta.*;
import Trueque.Trueque.modelos.Oferta;
import org.springframework.data.domain.*;

import java.util.*;

public interface IOfertaService {

    List<OfertaSalida> obtenerTodos();

    Page<OfertaSalida> obtenerTodoPaginados(Pageable pageable);

    OfertaSalida obtenerPorId (Long idOferta);

    OfertaSalida crear (OfertaGuardar ofertaGuardar);

    List<OfertaSalida> buscarPorTitulo(String titulo);

    OfertaSalida editar (OfertaModificar ofertaModificar);

    void eliminarPorId (Long idOferta);



}
