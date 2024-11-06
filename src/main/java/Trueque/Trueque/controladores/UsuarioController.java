package Trueque.Trueque.controladores;

import Trueque.Trueque.seguridad.dtos.UsuarioLogin;
import Trueque.Trueque.seguridad.dtos.UsuarioRegistrar;
import Trueque.Trueque.seguridad.dtos.UsuarioToken;
import Trueque.Trueque.seguridad.dtos.usuario.UsuarioModificar;
import Trueque.Trueque.seguridad.dtos.usuario.UsuarioSalida;
import Trueque.Trueque.seguridad.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<UsuarioToken> login(@RequestBody UsuarioLogin loginRequest) {
        System.out.println("JSON recibido: " + loginRequest);
        System.out.println("Correo: " + loginRequest.getCorreo());
        System.out.println("Contraseña: " + loginRequest.getContrasena());
        return ResponseEntity.ok(usuarioService.login(loginRequest));
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioToken> registro(
            @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
            @RequestPart("usuario") UsuarioRegistrar registroRequest) throws IOException {

        System.out.println("Cuerpo del json: " + registroRequest);
        System.out.println("imagen: " + fotoPerfil);
        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            registroRequest.setFotoPerfil(fotoPerfil.getBytes());
        }

        return ResponseEntity.ok(usuarioService.registro(registroRequest));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<List<UsuarioSalida>> mostrarTodos() {
        List<UsuarioSalida> usuarios = usuarioService.obtenerTodos();
        if (!usuarios.isEmpty()) {
            return ResponseEntity.ok(usuarios);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findbyID/{idUsuario}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<UsuarioSalida> buscarPorId(@PathVariable Long idUsuario) {
        UsuarioSalida salida = usuarioService.obtenenerPorId(idUsuario);
        if (salida != null) {
            return ResponseEntity.ok(salida);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{idUsuario}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<UsuarioSalida> editar(
            @PathVariable Long idUsuario,
            @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
            @RequestPart("usuario") UsuarioModificar usuarioModificar) throws IOException {

        usuarioModificar.setIdUsuario(idUsuario);

        if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
            usuarioModificar.setFotoPerfil(fotoPerfil.getBytes());
        }

        UsuarioSalida usuarioEditado = usuarioService.editar(idUsuario, usuarioModificar);

        return ResponseEntity.ok(usuarioEditado);
    }

}
