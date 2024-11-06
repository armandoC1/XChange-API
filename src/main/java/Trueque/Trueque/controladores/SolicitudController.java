package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.solicitud.SolicitudGuardar;
import Trueque.Trueque.dtos.solicitud.SolicitudModificar;
import Trueque.Trueque.dtos.solicitud.SolicitudSalida;
import Trueque.Trueque.servicios.interfaces.ISolicitudServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    @Autowired
    private ISolicitudServe solicitudServe;

    @GetMapping("/listPage")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<Page<SolicitudSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<SolicitudSalida> solicitudes = solicitudServe.obtenerTodosPaginados(pageable);
        if (solicitudes.hasContent()) {
            return ResponseEntity.ok(solicitudes);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<List<SolicitudSalida>> obtenerTodos() {
        List<SolicitudSalida> solicitudes = solicitudServe.obtenerTodos();
        if (!solicitudes.isEmpty()) {
            return ResponseEntity.ok(solicitudes);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('usuario')")
    public ResponseEntity<SolicitudSalida> crear(
            @RequestParam("imagenes") List<MultipartFile> imagenes,
            @RequestPart("solicitud") SolicitudGuardar solicitudGuardar) throws IOException {

        SolicitudSalida solicitudSalida = solicitudServe.crear(solicitudGuardar, imagenes);
        return ResponseEntity.ok(solicitudSalida);
    }

    @PutMapping("/edit/{idSolicitud}")
    @PreAuthorize("hasAuthority('usuario')")
    public ResponseEntity<SolicitudSalida> editar(
            @PathVariable Long idSolicitud,
            @RequestParam("imagenes") List<MultipartFile> imagenes,
            @RequestPart("solicitud") SolicitudModificar solicitudModificar) throws IOException {

        SolicitudSalida salida = solicitudServe.editar(idSolicitud, solicitudModificar, imagenes);
        if (salida != null) {
            return ResponseEntity.ok(salida);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findById/{idSolicitud}")
    @PreAuthorize("hasAuthority('usuario')")
    public ResponseEntity<SolicitudSalida> obtenerPorId(@PathVariable Long idSolicitud) {
        SolicitudSalida solicitud = solicitudServe.obtenerPorId(idSolicitud);
        if (solicitud != null) {
            return ResponseEntity.ok(solicitud);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{idSolicitud}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<String> delete(@PathVariable Long idSolicitud) {
        solicitudServe.eliminarPorId(idSolicitud);
        return ResponseEntity.ok("Solicitud eliminada exitosamente");
    }
}
