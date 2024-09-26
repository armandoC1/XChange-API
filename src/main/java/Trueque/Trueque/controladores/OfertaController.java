package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.oferta.*;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.servicios.interfaces.IOfertaService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private IOfertaService ofertaService;

    @Autowired
    private IOfertaRepository ofertaRepository;

    //http://localhost:8080/ofertas
    @GetMapping
    public ResponseEntity<Page<OfertaSalida>> mostrarTodosPaginados (Pageable pageable){
        Page<OfertaSalida> ofertas = ofertaService.obtenerTodoPaginados(pageable);

        if (ofertas.hasContent()){
            return  ResponseEntity.ok(ofertas);
        }
            return ResponseEntity.notFound().build();
    }

    //http://localhost:8080/ofertas/listado
    @GetMapping("/listado")
    public ResponseEntity<List<OfertaSalida>> obtenerTodos(){
        List<OfertaSalida> ofertas = ofertaService.obtenerTodos();
        if (!ofertas.isEmpty()){
            return ResponseEntity.ok(ofertas);
        }
            return ResponseEntity.notFound().build();
    }

    //http://localhost:8080/ofertas/save
    //aqui se deben pasar los datos pero la fecha y estado lo crea en automatico
    @PostMapping("/save")
    public ResponseEntity<OfertaSalida> crear (
            @RequestParam("imagenes") List<MultipartFile> imagenes,
            @RequestPart("oferta") OfertaGuardar oferta) throws IOException {

        OfertaSalida ofertaSalida = ofertaService.crear(oferta, imagenes);
        return ResponseEntity.ok(ofertaSalida);

    }

    //http://localhost:8080/ofertas/findById/0 <--aqui debe ir el id de la oferta que quieran buscar
    @GetMapping("/findById/{idOferta}")
    public ResponseEntity<OfertaSalida> obtenerPorId (@PathVariable Long idOferta, @RequestBody OfertaSalida ofertaSalida){
        OfertaSalida oferta = ofertaService.obtenerPorId(idOferta);
        if (oferta != null) {
            return ResponseEntity.ok(oferta);
        }
            return ResponseEntity.notFound().build();
    }

    //http://localhost:8080/ofertas/edit/0 <-- aqui debe ir el id de la oferta que quieren editar
    //aqui si deben decirle si el estado es activo o no pero la fecha la mantiene en la fecha que fue creada
    @PutMapping("/edit/{idOferta}")
    public ResponseEntity<OfertaSalida> editar (@PathVariable Long idOferta, @RequestBody OfertaModificar ofertaModificar){
        OfertaSalida salida = ofertaService.editar(ofertaModificar);
        if (salida != null) {
            return ResponseEntity.ok(salida);
        }
            return ResponseEntity.notFound().build();
    }

    //http://localhost:8080/ofertas/busar/ xxxx <-- aqui debe ir el nombre de lo que quieren buscar
    @GetMapping("/buscar/{titulo}")
    public ResponseEntity<List<OfertaSalida>> buscarPorTitulo(@PathVariable String titulo) {
        List<OfertaSalida> ofertas = ofertaService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(ofertas);
    }

    //http://localhost:8080/ofertas/delete/0 <--aqui debe ir el id de la oferta que se quiere borrar
    @DeleteMapping("/delete/{idOferta}")
    public ResponseEntity delete(@PathVariable Long idOferta){
        ofertaService.eliminarPorId(idOferta);
        return ResponseEntity.ok("Oferta eliminada excitosamente");
    }
}
