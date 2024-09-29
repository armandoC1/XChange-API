package Trueque.Trueque.servicios.interfaces;

import Trueque.Trueque.dtos.usuario.*;
import org.springframework.data.domain.*;

import java.io.IOException;
import java.util.*;

public interface IUsuarioService {

    List<UsuarioSalida> obtenerTodos();

    Page<UsuarioSalida> obtenerTodoPaginados(Pageable pageable);

    UsuarioSalida obtenenerPorId(Long idUsuario);

    UsuarioSalida crear(UsuarioGuardar usuarioGuardar) throws IOException;

    UsuarioSalida editar (UsuarioModificar usuarioModificar) throws IOException;

     void eliminarPorId (Long idUsuario);
}
