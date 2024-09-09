package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.usuario.*;
import Trueque.Trueque.modelos.Usuario;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
        Usuario usuario = modelMapper.map(usuarioGuardar, Usuario.class);
        usuario = usuarioRepository.save(usuario);
        return  modelMapper.map(usuario, UsuarioSalida.class);
    }

    @Override
    public UsuarioSalida editar(UsuarioModificar usuarioModificar) {
        Usuario usuario = modelMapper.map(usuarioModificar, Usuario.class);
        usuario = usuarioRepository.save(usuario);
        return  modelMapper.map(usuario, UsuarioSalida.class);
    }

    @Override
    public void eliminarPorId(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }
}
