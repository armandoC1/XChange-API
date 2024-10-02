package Trueque.Trueque.seguridad.repositorios;

import Trueque.Trueque.seguridad.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {


    Optional<Usuario> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}


