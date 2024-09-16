package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.solicitud.SolicitudGuardar;
import Trueque.Trueque.dtos.solicitud.SolicitudModificar;
import Trueque.Trueque.dtos.solicitud.SolicitudSalida;
import Trueque.Trueque.repositorios.ISolicitudRepository;
import Trueque.Trueque.servicios.interfaces.ISolicitudServe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.SocketImpl;
import java.util.List;

@RestController
@RequestMapping("/solicitudes")
public class SolicitudController {

    @Autowired
    private ISolicitudServe solicitudServe;

    @Autowired
    private ISolicitudRepository solicitudRepository;

    //http://localhost:8080/solicitudes
    @GetMapping
    public ResponseEntity<Page<SolicitudSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<SolicitudSalida> solicitudes = solicitudServe.obtenerTodosPaginados(pageable);

        if(solicitudes.hasContent()){
            return ResponseEntity.ok(solicitudes);
        }
            return ResponseEntity.notFound().build();

    }

    //http://localhost:8080/solicitudes/listado
    @GetMapping("/listado")
    public ResponseEntity<List<SolicitudSalida>> mostrarTodos(){
        List<SolicitudSalida> solicitud = solicitudServe.obtenerTodos();

        if (solicitud.isEmpty()){
            return ResponseEntity.notFound().build();
        }
            return ResponseEntity.ok(solicitud);
    }

    //http://localhost:8080/solicitudes/save
    @PostMapping("/save")
    public ResponseEntity<SolicitudSalida> crear (@RequestBody SolicitudGuardar solicitudGuardar){
        SolicitudSalida solicitudSalida = solicitudServe.crear(solicitudGuardar);
        return ResponseEntity.ok(solicitudSalida);
    }

    //http://localhost:8080/solicitudes/findById/0 <-- aqui debe ir el id
    @GetMapping("/findbyId/{idSolicitud}")
    public ResponseEntity<SolicitudSalida> obtenerPorId (@PathVariable Long idSolicitud, @RequestBody SolicitudModificar solicitudModificar){
        SolicitudSalida solicitudSalida = solicitudServe.obtenerPorId(idSolicitud);

        if (solicitudSalida != null){
            return ResponseEntity.ok(solicitudSalida);
        }
            return ResponseEntity.notFound().build();
    }

    //http://localhost:8080/solicitudes/edit/0 <-- aqui debe ir el id la solicitud que queiren modoifcar
    @PutMapping("/edit/{idSolicitud}")
    public ResponseEntity<SolicitudSalida> editar (@PathVariable Long idSolicitud, @RequestBody SolicitudModificar solicitudModificar){
        SolicitudSalida solicitudSalida = solicitudServe.editar(solicitudModificar);
        if (solicitudSalida != null){
            return ResponseEntity.ok(solicitudSalida);
        }
            return ResponseEntity.notFound().build();
    }

    //http://localhost:8080/solicitudes/delete/0 <-- aqui va el id del registro que quieran borrar
    @DeleteMapping("/delete/{idSolicitud}")
    public ResponseEntity delete ( @PathVariable Long idSolicitud){
            solicitudServe.eliminarPorId(idSolicitud);
            return ResponseEntity.ok("Solicitud eliminada exitosamente");
    }

}
