package Trueque.Trueque.controladores;

import Trueque.Trueque.modelos.Coincidencia;
import Trueque.Trueque.servicios.implementaciones.CoincidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/coincidencia")
public class CoincidenciaController {

    @Autowired
    private CoincidenciaService coincidenciaService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('usuario')")
    public Coincidencia crearCoincidencia(@RequestParam Long idOferta, @RequestParam Long idSolicitud) {
        return coincidenciaService.crearCoincidencia(idOferta, idSolicitud);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public Coincidencia obtenerCoincidenciaPorId(@PathVariable Long id) {
        return coincidenciaService.obtenerCoincidenciaPorId(id);
    }
}
