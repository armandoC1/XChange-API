package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.mensaje.*;
import Trueque.Trueque.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController()
@RequestMapping("/api/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    @PostMapping("/sendMenssage")
    @PreAuthorize("hasAuthority('usuario')")
    public MensajeSalida guardarMensaje(@RequestBody MensajeGuardar mensajeGuardarDTO) {
        return mensajeService.guardarMensaje(mensajeGuardarDTO);
    }

    @GetMapping("/findByOfert/{idOferta}")
    @PreAuthorize("hasAuthority('usuario')")
    public List<MensajeSalida> obtenerMensajesPorOferta(@PathVariable Long idOferta) {
        return mensajeService.obtenerMensajesPorOferta(idOferta);
    }

    @GetMapping("/chat")
    @PreAuthorize("hasAuthority('usuario')")
    public List<MensajeSalida> obtenerMensajesEntreUsuarios(
            @RequestParam Long idOferta,
            @RequestParam Long idRemitente,
            @RequestParam Long idDestinatario) {
        return mensajeService.obtenerMensajesEntreUsuarios(idOferta, idRemitente, idDestinatario);
    }
}
