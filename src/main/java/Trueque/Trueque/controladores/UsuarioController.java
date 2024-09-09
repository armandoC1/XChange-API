package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.usuario.*;
import Trueque.Trueque.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<UsuarioSalida>> mostrarTodosPaginados(Pageable pageable){
        Page<UsuarioSalida> usuarios = usuarioService.obtenerTodoPaaginados(pageable);
        if (usuarios.hasContent()){
            return ResponseEntity.ok(usuarios);
        }
             return ResponseEntity.notFound().build();
    }

    @GetMapping("/listado")
    public ResponseEntity<List<UsuarioSalida>> mostrarTodos(){
        List<UsuarioSalida> usuarios = usuarioService.obtenerTodos();
        if (!usuarios.isEmpty()){
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<UsuarioSalida> crear (@RequestBody UsuarioGuardar usuarioGuardar){
        UsuarioSalida usuarios = usuarioService.crear(usuarioGuardar);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/find/{idUsuario}")
    public ResponseEntity<UsuarioSalida> buscarPorId (@PathVariable Long idUsuario, @RequestBody UsuarioSalida usuarioSalida){
        UsuarioSalida salida = usuarioService.obtenenerPorId(idUsuario);
        if (salida != null){
            return ResponseEntity.ok(salida);
        }
        return ResponseEntity.notFound().build();

    }

    @PutMapping("/edit/{idUsuario}")
    public ResponseEntity<UsuarioSalida> editar (@PathVariable Long idUsuario, @RequestBody UsuarioModificar usuarioModificar){
        UsuarioSalida salida = usuarioService.editar(usuarioModificar);
        return ResponseEntity.ok(salida);
    }

    @DeleteMapping("/delete/{usuarioId}")
    public ResponseEntity eliminar (@PathVariable Long usuarioId){
        usuarioService.eliminarPorId(usuarioId);
        return ResponseEntity.ok("Usuario eliminado excitosamente");
    }
}
