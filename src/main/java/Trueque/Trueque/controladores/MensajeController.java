package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.mensaje.*;
import Trueque.Trueque.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    // Guardar un nuevo mensaje
    @PostMapping("/guardar")
    public ResponseEntity<MensajeSalida> guardarMensaje(@RequestBody MensajeGuardar mensajeGuardar) {
        MensajeSalida mensajeSalida = mensajeService.guardarMensaje(mensajeGuardar);
        return ResponseEntity.ok(mensajeSalida);
    }

    // Modificar un mensaje
    @PutMapping("/modificar")
    public ResponseEntity<MensajeSalida> modificarMensaje(@RequestBody MensajeModificar mensajeModificar) {
        MensajeSalida mensajeSalida = mensajeService.modificarMensaje(mensajeModificar);
        return ResponseEntity.ok(mensajeSalida);
    }

    // Obtener mensajes por coincidencia
    @GetMapping("/coincidencia/{idCoincidencia}")
    public ResponseEntity<List<MensajeSalida>> obtenerMensajesPorCoincidencia(@PathVariable Long idCoincidencia) {
        List<MensajeSalida> mensajes = mensajeService.obtenerMensajesPorCoincidencia(idCoincidencia);
        return ResponseEntity.ok(mensajes);
    }

    // Obtener mensajes enviados por un usuario
    @GetMapping("/enviados/{idRemitente}")
    public ResponseEntity<List<MensajeSalida>> obtenerMensajesEnviados(@PathVariable Long idRemitente) {
        List<MensajeSalida> mensajes = mensajeService.obtenerMensajesEnviadosPorUsuario(idRemitente);
        return ResponseEntity.ok(mensajes);
    }

    // Obtener mensajes recibidos por un usuario
    @GetMapping("/recibidos/{idDestinatario}")
    public ResponseEntity<List<MensajeSalida>> obtenerMensajesRecibidos(@PathVariable Long idDestinatario) {
        List<MensajeSalida> mensajes = mensajeService.obtenerMensajesRecibidosPorUsuario(idDestinatario);
        return ResponseEntity.ok(mensajes);
    }
}
