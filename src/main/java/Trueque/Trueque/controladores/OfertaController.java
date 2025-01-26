package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.oferta.*;
import Trueque.Trueque.repositorios.IOfertaRepository;
import Trueque.Trueque.servicios.interfaces.IOfertaService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://xchangesv.es",
        "https://api.xchangesv.es",
        "https://api.xchangesv.es:8080",
        "http://xchangesv.es",
        "http://api.xchangesv.es",
        "http://api.xchangesv.es:8080"
})
@RestController
@RequestMapping("/ofertas")
public class OfertaController {

    @Autowired
    private IOfertaService ofertaService;

    @Autowired
    private IOfertaRepository ofertaRepository;

    @GetMapping("/listPage")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<Page<OfertaSalida>> mostrarTodosPaginados(Pageable pageable) {
        Page<OfertaSalida> ofertas = ofertaService.obtenerTodoPaginados(pageable);

        if (ofertas.hasContent()) {
            return ResponseEntity.ok(ofertas);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<List<OfertaSalida>> obtenerTodos() {
        List<OfertaSalida> ofertas = ofertaService.obtenerTodos();
        if (!ofertas.isEmpty()) {
            return ResponseEntity.ok(ofertas);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<OfertaSalida> crear(
            @RequestParam("imagenes") List<MultipartFile> imagenes,
            @RequestPart("oferta") OfertaGuardar oferta) throws IOException {
        System.out.println("imagenes: "+ imagenes);

        OfertaSalida ofertaSalida = ofertaService.crear(oferta, imagenes);
        return ResponseEntity.ok(ofertaSalida);
    }

    @GetMapping("/misOfertas")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public List<OfertaSalida> obtenerMisOfertas(@RequestParam Long userId) {
        return ofertaService.obtenerOfertasPorUsuario(userId);
    }

    @GetMapping("/findById/{idOferta}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<OfertaSalida> obtenerPorId(@PathVariable Long idOferta) {
        OfertaSalida oferta = ofertaService.obtenerPorId(idOferta);
        if (oferta != null) {
            return ResponseEntity.ok(oferta);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/edit/{idOferta}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<OfertaSalida> editar(
            @PathVariable Long idOferta,
            @RequestParam("imagenes") List<MultipartFile> imagenes,
            @RequestPart("oferta") OfertaModificar ofertaModificar) throws IOException {

        OfertaSalida salida = ofertaService.editar(idOferta, ofertaModificar, imagenes);

        if (salida != null) {
            return ResponseEntity.ok(salida);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findByTitle/{titulo}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity<List<OfertaSalida>> buscarPorTitulo(@PathVariable String titulo) {
        List<OfertaSalida> ofertas = ofertaService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(ofertas);
    }

    @DeleteMapping("/delete/{idOferta}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('usuario')")
    public ResponseEntity delete(@PathVariable Long idOferta) {
        ofertaService.eliminarPorId(idOferta);
        return ResponseEntity.ok("Oferta eliminada excitosamente");
    }
}
