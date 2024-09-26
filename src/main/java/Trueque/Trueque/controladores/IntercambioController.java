package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.intercambio.*;
import Trueque.Trueque.servicios.implementaciones.IntercambioService;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/intercambios")
public class IntercambioController {

    private IntercambioService intercambioService;
    @GetMapping("/listado")
    public List<IntercambioSalida> obtenerTodos() {
        return intercambioService.obtenerTodos();
    }

    // Obtener intercambios paginados
    @GetMapping("/paginados")
    public Page<IntercambioSalida> obtenerTodoPaginados(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCompletado") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return intercambioService.obtenerTodoPaginados(pageable);
    }

    // Obtener intercambio por ID
    @GetMapping("edit/{id}")
    public IntercambioSalida obtenerPorId(@PathVariable("id") Long id) {
        return intercambioService.obtenerPorId(id);
    }

    // Confirmar intercambio (crear intercambio)
    @PostMapping("/confirmar")
    public IntercambioSalida confirmarIntercambio(@RequestBody IntercambioGuardar intercambioGuardar) {
        return intercambioService.crear(intercambioGuardar);
    }

}
