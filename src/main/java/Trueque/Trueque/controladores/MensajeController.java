package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.mensaje.*;
import Trueque.Trueque.servicios.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://xchangesv.es",
        "https://api.xchangesv.es",
        "https://api.xchangesv.es:8080",
        "http://xchangesv.es",
        "http://api.xchangesv.es",
        "http://api.xchangesv.es:8080"
})
@RestController()
@RequestMapping("/mensajes")
public class MensajeController {

    @Autowired
    private IMensajeService mensajeService;

    @PostMapping("/sendMenssage")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public MensajeSalida guardarMensaje(@RequestBody MensajeGuardar mensajeGuardarDTO) {
        return mensajeService.guardarMensaje(mensajeGuardarDTO);
    }

    @GetMapping("/findByOfert/{idOferta}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public List<MensajeSalida> obtenerMensajesPorOferta(@PathVariable Long idOferta) {
        return mensajeService.obtenerMensajesPorOferta(idOferta);
    }

    @GetMapping("/chat")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public Map<Long, Map<Long, List<MensajeSalida>>> obtenerMensajesAgrupados(
            @RequestParam Long userId) {
        return mensajeService.obtenerMensajesAgrupados(userId);
    }

}
