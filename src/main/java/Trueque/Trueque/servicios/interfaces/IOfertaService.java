package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.oferta.*;
import org.springframework.data.domain.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

public interface IOfertaService {

    List<OfertaSalida> obtenerTodos();

    Page<OfertaSalida> obtenerTodoPaginados(Pageable pageable);

    OfertaSalida obtenerPorId (Long idOferta);

    OfertaSalida crear (OfertaGuardar ofertaGuardar,  List<MultipartFile> imagenes) throws IOException; //revisar posible error

    List<OfertaSalida> buscarPorTitulo(String titulo);

    OfertaSalida editar(Long idOferta, OfertaModificar ofertaModificar, List<MultipartFile> imagenes) throws IOException;

    void eliminarPorId (Long idOferta);



}
