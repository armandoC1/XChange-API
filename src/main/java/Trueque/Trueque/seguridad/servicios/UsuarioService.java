package Trueque.Trueque.seguridad.servicios;

import Trueque.Trueque.seguridad.dtos.usuario.UsuarioModificar;
import Trueque.Trueque.seguridad.dtos.usuario.UsuarioSalida;
import Trueque.Trueque.seguridad.dtos.UsuarioLogin;
import Trueque.Trueque.seguridad.dtos.UsuarioRegistrar;
import Trueque.Trueque.seguridad.dtos.UsuarioToken;
import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.seguridad.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private RolService rolService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UsuarioToken login(UsuarioLogin correoRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(correoRequest.getCorreo(),
                correoRequest.getContrasena()));
        Usuario usuario = userRepository.findByCorreo(correoRequest.getCorreo()).orElseThrow();
        Long idUsuario = usuario.getIdUsuario();
        String token = jwtService.getToken(usuario);
        return UsuarioToken.builder()
                .idUsuario(idUsuario)
                .token(token)
                .build();
    }

    public UsuarioToken registro(UsuarioRegistrar registroRequest) {

        if (userRepository.existsByCorreo(registroRequest.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        Usuario usuario = Usuario.builder()
                .nombre(registroRequest.getNombre())
                .correo(registroRequest.getCorreo())
                .ubicacion(registroRequest.getUbicacion())
                .numeroTelefono(registroRequest.getNumeroTelefono())
                .contrasena(passwordEncoder.encode(registroRequest.getContrasena()))
                .rol(rolService.obtenerPorId(registroRequest.getIdRol()))
                .build();

        byte[] fotoPerfil = registroRequest.getFotoPerfil();
        if (fotoPerfil != null && fotoPerfil.length > 0) {
            usuario.setFotoPerfil(fotoPerfil);
        }

        usuario = userRepository.save(usuario);

        String token = jwtService.getToken(usuario);

        return UsuarioToken.builder()
                .token(token)
                .build();
    }

    public UsuarioSalida obtenenerPorId(Long idUsuario) {

        Optional<Usuario>  usuarioOpt = userRepository.findById(idUsuario);

        if (usuarioOpt.isPresent()) {

            Usuario usuario = usuarioOpt.get();

            UsuarioSalida salida = new UsuarioSalida();

            salida.setIdUsuario(usuario.getIdUsuario());
            salida.setNombre(usuario.getNombre());
            salida.setCorreo(usuario.getCorreo());
            salida.setNumeroTelefono(usuario.getNumeroTelefono());
            salida.setFotoPerfil(usuario.getFotoPerfil());
            salida.setUbicacion(usuario.getUbicacion());
            salida.setFechaCreacion(usuario.getFechaCreacion());
            salida.setReputacion(usuario.getReputacion());
            salida.setEstado(usuario.getEstado());

            return salida;
        } else {
            return null;
        }
    }

    public List<UsuarioSalida> obtenerTodos() {
        List<Usuario> usuarios = userRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario , UsuarioSalida.class))
                .collect(Collectors.toList());
    }

    public UsuarioSalida editar(Long idUsuario, UsuarioModificar usuarioEditar) {
        Usuario usuario = userRepository.findById(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (usuarioEditar.getNombre() != null && !usuarioEditar.getNombre().isEmpty()) {
            usuario.setNombre(usuarioEditar.getNombre());
        }

        if (usuarioEditar.getCorreo() != null && !usuarioEditar.getCorreo().isEmpty()) {
            if (userRepository.existsByCorreo(usuarioEditar.getCorreo()) && !usuario.getCorreo().equals(usuarioEditar.getCorreo())) {
                throw new IllegalArgumentException("El correo ya está en uso");
            }
            usuario.setCorreo(usuarioEditar.getCorreo());
        }

        if (usuarioEditar.getContrasena() != null && !usuarioEditar.getContrasena().isEmpty()) {
            String contrasenaCifrada = passwordEncoder.encode(usuarioEditar.getContrasena());
            usuario.setContrasena(contrasenaCifrada);
        }

        byte[] fotoPerfil = usuarioEditar.getFotoPerfil();

        if (fotoPerfil != null && fotoPerfil.length > 0) {
            usuario.setFotoPerfil(fotoPerfil);
        }

        if (usuarioEditar.getIdRol() != null) {
            usuario.setRol(rolService.obtenerPorId(usuarioEditar.getIdRol()));
        }

        usuario = userRepository.save(usuario);

        return modelMapper.map(usuario, UsuarioSalida.class);
    }

}