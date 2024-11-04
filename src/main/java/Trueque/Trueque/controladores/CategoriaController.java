package Trueque.Trueque.controladores;

import Trueque.Trueque.dtos.categoria.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import Trueque.Trueque.servicios.interfaces.ICategoriaService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

//    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    //http://localhost:8080/categorias
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<Page<CategoriaSalida>> mostrarTodosPaginados(Pageable pageable){
        Page<CategoriaSalida> categorias = categoriaService.obtenerTodosPaginados(pageable);
        if (categorias.hasContent()){
            return ResponseEntity.ok(categorias);
        }
        return ResponseEntity.notFound().build();
    }

//    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    //http://localhost:8080/categorias/lista
    @GetMapping("/lista")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<CategoriaSalida>> mostrarTodos(){
        List<CategoriaSalida> caegorias = categoriaService.obtenerTodos();
        if(!caegorias.isEmpty()){
            return ResponseEntity.ok(caegorias);
        }
        return ResponseEntity.notFound().build();
    }

//    @PreAuthorize("hasAnyRole('admin', 'usuario')")
    //http://localhost:8080/categorias/find/0 <-- aqui va el id que quieren buscar
    @GetMapping("find/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CategoriaSalida> buscarPorId(@PathVariable Long id){
        CategoriaSalida categoria =categoriaService.obtenerPorId(id);

        if (categoria != null){
            return ResponseEntity.ok(categoria);
        }
        return ResponseEntity.notFound().build();
    }

//    @PreAuthorize("hasRole('admin')")
    //http://localhost:8080/categorias/save
    @PostMapping("/save")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CategoriaSalida> crear (@RequestBody CategoriaGuardar categoriaGuardar){
        CategoriaSalida categoria = categoriaService.crear(categoriaGuardar);
        return ResponseEntity.ok(categoria);
    }

//    @PreAuthorize("hasRole('admin')")
    //http://localhost:8080/categorias/edit/0 <-- aqui va el id que se quiere editar
@PreAuthorize("permitAll()")
    @PutMapping("/edit/{id}")
    public ResponseEntity<CategoriaSalida> editar (@PathVariable Long id, @RequestBody CategoriaModificar categoriaModificar){
        CategoriaSalida categoria = categoriaService.editar(id, categoriaModificar);
        return ResponseEntity.ok(categoria);
    }

//    @PreAuthorize("hasRole('admin')")
    //http://localhost:8080/categorias/delete/0 <-- aqui va el id que se quiere eliminar
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity eliminar (@PathVariable Long id){
        categoriaService.eliminarPorId(id);
        return ResponseEntity.ok("Categoria eliminada exitosamente");
    }
}
