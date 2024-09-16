package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.usuario.*;
import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.*;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<UsuarioSalida> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario , UsuarioSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UsuarioSalida> obtenerTodoPaaginados(Pageable pageable) {
        Page<Usuario> page = usuarioRepository.findAll(pageable);

        List<UsuarioSalida> usuariosDto = page.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioSalida.class))
                .collect(Collectors.toList());
        return new PageImpl<>(usuariosDto, page.getPageable(), page.getTotalElements());
    }

    @Override
    public UsuarioSalida obtenenerPorId(Long idUsuario) {
        return modelMapper.map(usuarioRepository.findById(idUsuario).get(), UsuarioSalida.class);
    }

    @Override
    public UsuarioSalida crear(UsuarioGuardar usuarioGuardar) {

        // Mapeo del DTO UsuarioGuardar a la entidad Usuario
        Usuario usuario = modelMapper.map(usuarioGuardar, Usuario.class);

        // Ya no necesitas obtener el MultipartFile, sino trabajar directamente con el byte[] que ya tienes
        byte[] fotoPerfil = usuarioGuardar.getFotoPerfil();
        if (fotoPerfil != null && fotoPerfil.length > 0) {
            usuario.setFotoPerfil(fotoPerfil);
        }

        // Guardar el usuario en la base de datos
        usuario = usuarioRepository.save(usuario);

        // Mapeo de la entidad Usuario a UsuarioSalida
        return modelMapper.map(usuario, UsuarioSalida.class);
    }

    @Override
    public UsuarioSalida editar(UsuarioModificar usuarioModificar) throws IOException {

        // Buscar el usuario existente en la base de datos por su ID
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioModificar.getIdUsuario());
        if (!usuarioOptional.isPresent()) {
            throw new RuntimeException("El usuario con el ID proporcionado no existe");
        }

        Usuario usuarioExistente = usuarioOptional.get();

        // Mapear los campos actualizados del usuario
        modelMapper.map(usuarioModificar, usuarioExistente);

        // Verificar si se ha proporcionado una nueva foto de perfil
        if (usuarioModificar.getFotoPerfil() != null && usuarioModificar.getFotoPerfil().length > 0) {
            usuarioExistente.setFotoPerfil(usuarioModificar.getFotoPerfil());
        }

        // Guardar los cambios en la base de datos
        usuarioExistente = usuarioRepository.save(usuarioExistente);

        // Devolver el usuario actualizado
        return modelMapper.map(usuarioExistente, UsuarioSalida.class);
    }


    @Override
    public void eliminarPorId(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
}
