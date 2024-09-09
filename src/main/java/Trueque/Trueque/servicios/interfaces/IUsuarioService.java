package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.usuario.*;
import org.springframework.data.domain.*;

import java.util.*;

public interface IUsuarioService {

    List<UsuarioSalida> obtenerTodos();

    Page<UsuarioSalida> obtenerTodoPaaginados(Pageable pageable);

    UsuarioSalida obtenenerPorId(Long idUsuario);

    UsuarioSalida crear(UsuarioGuardar usuarioGuardar);

    UsuarioSalida editar (UsuarioModificar usuarioModificar);

     void eliminarPorId (Long idUsuario);
}
