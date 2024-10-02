package Trueque.Trueque.controladores;

import Trueque.Trueque.modelos.Coincidencia;
import Trueque.Trueque.servicios.implementaciones.CoincidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/coincidencia")
public class CoincidenciaController {

    @Autowired
    private CoincidenciaService coincidenciaService;

    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    @PostMapping("/crear")
    public Coincidencia crearCoincidencia(@RequestParam Long idOferta, @RequestParam Long idSolicitud) {
        return coincidenciaService.crearCoincidencia(idOferta, idSolicitud);
    }

    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    // Endpoint para obtener una coincidencia por ID
    @GetMapping("/{id}")
    public Coincidencia obtenerCoincidenciaPorId(@PathVariable Long id) {
        return coincidenciaService.obtenerCoincidenciaPorId(id);
    }
}
