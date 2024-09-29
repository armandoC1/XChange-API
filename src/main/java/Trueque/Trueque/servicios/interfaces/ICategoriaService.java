package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.categoria.CategoriaGuardar;
import Trueque.Trueque.dtos.categoria.CategoriaModificar;
import Trueque.Trueque.dtos.categoria.CategoriaSalida;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.*;

public interface ICategoriaService {

    List<CategoriaSalida> obtenerTodos();

    Page<CategoriaSalida> obtenerTodosPaginados(Pageable pageable);

    CategoriaSalida obtenerPorId(Long id);

    CategoriaSalida crear(CategoriaGuardar categoriaGuardar);

    CategoriaSalida editar(CategoriaModificar categoriaModificar);

    void eliminarPorId(Long id);
}
