package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.usuario.*;
import Trueque.Trueque.servicios.interfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

//    @PostMapping("/save")
//    public ResponseEntity<UsuarioSalida> crear(
//            @RequestPart("nombre") String nombre,
//            @RequestPart("correo") String correo,
//            @RequestPart("contrasena") String contrasena,
//            @RequestPart("numeroTelefono") String numeroTelefono,
//            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
//            @RequestPart("ubicacion") String ubicacion) throws IOException {
//
//        UsuarioGuardar usuarioGuardar = new UsuarioGuardar();
//        usuarioGuardar.setNombre(nombre);
//        usuarioGuardar.setCorreo(correo);
//        usuarioGuardar.setContrasena(contrasena);
//        usuarioGuardar.setNumeroTelefono(numeroTelefono);
//        usuarioGuardar.setUbicacion(ubicacion);
//
//        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
//            usuarioGuardar.setFotoPerfil(fotoPerfil.getBytes());
//        }
//
//        UsuarioSalida usuarioCreado = usuarioService.crear(usuarioGuardar);
//        return ResponseEntity.ok(usuarioCreado);
//    }

    @PostMapping("/save")
    public ResponseEntity<UsuarioSalida> crear(
            @RequestParam("fotoPerfil") MultipartFile fotoPerfil,
            @RequestPart("usuario") UsuarioGuardar usuario) throws IOException {

        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            usuario.setFotoPerfil(fotoPerfil.getBytes());
        }

        UsuarioSalida usuarioCreado = usuarioService.crear(usuario);
        return ResponseEntity.ok(usuarioCreado);
    }


    @GetMapping("/find/{idUsuario}")
    public ResponseEntity<UsuarioSalida> buscarPorId (@PathVariable Long idUsuario, @RequestBody UsuarioSalida usuarioSalida){
        UsuarioSalida salida = usuarioService.obtenenerPorId(idUsuario);
        if (salida != null){
            return ResponseEntity.ok(salida);
        }
        return ResponseEntity.notFound().build();

    }

//    @PutMapping("/edit/{idUsuario}")
//    public ResponseEntity<UsuarioSalida> editar(
//            @PathVariable Long idUsuario,
//            @RequestPart(value = "nombre", required = false) String nombre,
//            @RequestPart(value = "correo", required = false) String correo,
//            @RequestPart(value = "contrasena", required = false) String contrasena,
//            @RequestPart(value = "numeroTelefono", required = false) String numeroTelefono,
//            @RequestPart(value = "reputacion", required = false) Double reputacion,
//            @RequestPart(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
//            @RequestPart(value = "ubicacion", required = false) String ubicacion) throws IOException {
//
//        UsuarioModificar usuarioModificar = new UsuarioModificar();
//        usuarioModificar.setIdUsuario(idUsuario);
//        usuarioModificar.setNombre(nombre);
//        usuarioModificar.setCorreo(correo);
//        usuarioModificar.setContrasena(contrasena);
//        usuarioModificar.setNumeroTelefono(numeroTelefono);
//        usuarioModificar.setReputacion(reputacion);
//        usuarioModificar.setUbicacion(ubicacion);
//
//        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
//            usuarioModificar.setFotoPerfil(fotoPerfil.getBytes());
//        }
//
//        UsuarioSalida usuarioEditado = usuarioService.editar(usuarioModificar);
//
//        return ResponseEntity.ok(usuarioEditado);
//    }

    @PutMapping("/edit/{idUsuario}")
    public ResponseEntity<UsuarioSalida> editar(
            @PathVariable Long idUsuario,
            @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
            @RequestPart("usuario") UsuarioModificar usuarioModificar) throws IOException {

        usuarioModificar.setIdUsuario(idUsuario);

        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            usuarioModificar.setFotoPerfil(fotoPerfil.getBytes());
        }

        UsuarioSalida usuarioEditado = usuarioService.editar(usuarioModificar);

        return ResponseEntity.ok(usuarioEditado);
    }


    @DeleteMapping("/delete/{usuarioId}")
    public ResponseEntity eliminar (@PathVariable Long usuarioId){
        usuarioService.eliminarPorId(usuarioId);
        return ResponseEntity.ok("Usuario eliminado excitosamente");
    }
}
