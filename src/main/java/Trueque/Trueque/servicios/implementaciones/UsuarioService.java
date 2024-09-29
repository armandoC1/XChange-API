package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.usuario.*;
import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.*;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Obtener todos los usuarios
    @Override
    public List<UsuarioSalida> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario , UsuarioSalida.class))
                .collect(Collectors.toList());
    }

    // 2. Obtener todos los usuarios con paginación
    @Override
    public Page<UsuarioSalida> obtenerTodoPaginados(Pageable pageable) {
        Page<Usuario> page = usuarioRepository.findAll(pageable);

        List<UsuarioSalida> usuariosDto = page.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioSalida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(usuariosDto, page.getPageable(), page.getTotalElements());
    }

    // 3. Obtener usuario por ID
    @Override
    public UsuarioSalida obtenenerPorId(Long idUsuario) {
        return modelMapper.map(usuarioRepository.findById(idUsuario).get(), UsuarioSalida.class);
    }

    // 4. Crear nuevo usuario (con cifrado de contraseña)
    @Override
    public UsuarioSalida crear(UsuarioGuardar usuarioGuardar) {
        Usuario usuario = modelMapper.map(usuarioGuardar, Usuario.class);

        // Cifrar la contraseña antes de guardar
        String contrasenaCifrada = passwordEncoder.encode(usuarioGuardar.getContrasena());
        usuario.setContrasena(contrasenaCifrada);

        // Asignar foto de perfil si existe
        byte[] fotoPerfil = usuarioGuardar.getFotoPerfil();
        if (fotoPerfil != null && fotoPerfil.length > 0) {
            usuario.setFotoPerfil(fotoPerfil);
        }

        usuario = usuarioRepository.save(usuario);
        return modelMapper.map(usuario, UsuarioSalida.class);
    }

    // 5. Editar un usuario existente
    @Override
    public UsuarioSalida editar(UsuarioModificar usuarioModificar) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioModificar.getIdUsuario());
        if (!usuarioOptional.isPresent()) {
            throw new RuntimeException("El usuario con el ID proporcionado no existe");
        }

        Usuario usuarioExistente = usuarioOptional.get();

        // Actualizar datos del usuario
        modelMapper.map(usuarioModificar, usuarioExistente);

        // Cifrar nueva contraseña si ha sido proporcionada
        if (usuarioModificar.getContrasena() != null && !usuarioModificar.getContrasena().isEmpty()) {
            String contrasenaCifrada = passwordEncoder.encode(usuarioModificar.getContrasena());
            usuarioExistente.setContrasena(contrasenaCifrada);
        }

        // Asignar nueva foto de perfil si existe
        if (usuarioModificar.getFotoPerfil() != null && usuarioModificar.getFotoPerfil().length > 0) {
            usuarioExistente.setFotoPerfil(usuarioModificar.getFotoPerfil());
        }

        usuarioExistente = usuarioRepository.save(usuarioExistente);
        return modelMapper.map(usuarioExistente, UsuarioSalida.class);
    }

    // 6. Eliminar usuario por ID
    @Override
    public void eliminarPorId(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    // 7. Método para validar el inicio de sesión
    public boolean validarCredenciales(String correo, String contrasena) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            // Validar la contraseña utilizando BCrypt
            return passwordEncoder.matches(contrasena, usuario.getContrasena());
        }
        return false;  // Retornar false si el usuario no existe o la contraseña es incorrecta
    }
}
