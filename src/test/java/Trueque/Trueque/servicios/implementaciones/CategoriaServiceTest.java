package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.categoria.CategoriaGuardar;
import Trueque.Trueque.dtos.categoria.CategoriaModificar;
import Trueque.Trueque.dtos.categoria.CategoriaSalida;
import Trueque.Trueque.servicios.interfaces.ICategoriaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoriaServiceTest {

    @Autowired
    private ICategoriaService categoriaService;

    //prueba pasada
    @Test
    void t1_crear() {
        CategoriaSalida salida = categoriaService.crear(new CategoriaGuardar("Categoría de prueba"));
        assertNotNull(salida, "La categoría creada no debe ser nula");
    }

    //test pasado con excito
    @Test
    void t2_obtenerTodos() {
        int actual = categoriaService.obtenerTodos().size();
        assertTrue(actual > 0, "Debe haber al menos una categoría");
    }

    //test pasado
    @Test
    void t3_obtenerTodosPaginados() {
        int actual = categoriaService.obtenerTodosPaginados(PageRequest.of(0, 10)).getSize();
        assertTrue(actual > 0, "Debe haber al menos una categoría en la primera página");
    }

    //test pasado con excito
    @Test
    void t4_obtenerPorId() {
        CategoriaSalida salida = categoriaService.obtenerPorId(1L); // Cambiado a Long
        assertNotNull(salida, "La categoría obtenida no debe ser nula");
    }

    //test pasado
    @Test
    void t5_editar() {
        CategoriaSalida salida = categoriaService.editar(new CategoriaModificar(1L, "Categoría modificada")); // Cambiado a Long
        assertNotNull(salida, "La categoría modificada no debe ser nula");
        assertEquals("Categoría modificada", salida.getNombre(), "El nombre de la categoría debe coincidir con el nuevo valor");
    }

    //test pasado
    @Test
    void t6_eliminarPorId() {
        assertDoesNotThrow(() -> categoriaService.eliminarPorId(12L), "La eliminación no debe lanzar ninguna excepción"); // Cambiado a Long
    }
}
