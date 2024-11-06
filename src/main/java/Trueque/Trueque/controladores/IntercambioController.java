package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.intercambio.*;
import Trueque.Trueque.servicios.implementaciones.IntercambioService;
import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/intercambios")
public class IntercambioController {

    private IntercambioService intercambioService;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public List<IntercambioSalida> obtenerTodos() {
        return intercambioService.obtenerTodos();
    }

    @GetMapping("/lisPage")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public Page<IntercambioSalida> obtenerTodoPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCompletado") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return intercambioService.obtenerTodoPaginados(pageable);
    }

    @GetMapping("/findById/{id}")
    @PreAuthorize("hasAuthority('usuario')")
    public IntercambioSalida obtenerPorId(@PathVariable("id") Long id) {
        return intercambioService.obtenerPorId(id);
    }

    @PostMapping("/confirm")
    @PreAuthorize(" hasAuthority('usuario')")
    public IntercambioSalida confirmarIntercambio(@RequestBody IntercambioGuardar intercambioGuardar) {
        return intercambioService.crear(intercambioGuardar);
    }

}
