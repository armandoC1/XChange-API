package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService  implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Buscar al usuario por correo en tu base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Crear UserDetails basado en tu entidad Usuario
        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena()) // Ya debería estar encriptada
                .roles("USER") // Agrega roles si es necesario
                .build();
    }
}
