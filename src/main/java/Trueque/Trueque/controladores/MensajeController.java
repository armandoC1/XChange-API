package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.mensaje.*;
import Trueque.Trueque.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    @PostMapping("/enviar")
    public MensajeSalida guardarMensaje(@RequestBody MensajeGuardar mensajeGuardarDTO) {
        return mensajeService.guardarMensaje(mensajeGuardarDTO);
    }

    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    @GetMapping("/oferta/{idOferta}")
    public List<MensajeSalida> obtenerMensajesPorOferta(@PathVariable Long idOferta) {
        return mensajeService.obtenerMensajesPorOferta(idOferta);
    }

    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    @GetMapping("/chat")
    public List<MensajeSalida> obtenerMensajesEntreUsuarios(
            @RequestParam Long idOferta,
            @RequestParam Long idRemitente,
            @RequestParam Long idDestinatario) {
        return mensajeService.obtenerMensajesEntreUsuarios(idOferta, idRemitente, idDestinatario);
    }
}
