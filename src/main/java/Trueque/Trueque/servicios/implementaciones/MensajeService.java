package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.mensaje.MensajeGuardar;
import Trueque.Trueque.dtos.mensaje.MensajeModificar;
import Trueque.Trueque.dtos.mensaje.MensajeSalida;
import Trueque.Trueque.modelos.*;
import Trueque.Trueque.repositorios.CoincidenciaRepository;
import Trueque.Trueque.repositorios.IMensajeRepository;
import Trueque.Trueque.repositorios.IUsuarioRepository;
import Trueque.Trueque.servicios.interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MensajeService implements IMensajeService {
    @Autowired
    private IMensajeRepository mensajeRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private CoincidenciaRepository coincidenciaRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Guardar un nuevo mensaje
    @Override
    public MensajeSalida guardarMensaje(MensajeGuardar mensajeGuardar) {
        Mensaje mensaje = new Mensaje();

        // Buscar la coincidencia y los usuarios remitente/destinatario
        Coincidencia coincidencia = coincidenciaRepository.findById(mensajeGuardar.getIdCoincidencia())
                .orElseThrow(() -> new RuntimeException("Coincidencia no encontrada"));
        Usuario remitente = usuarioRepository.findById(mensajeGuardar.getIdRemitente())
                .orElseThrow(() -> new RuntimeException("Remitente no encontrado"));
        Usuario destinatario = usuarioRepository.findById(mensajeGuardar.getIdDestinatario())
                .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        // Setear los valores en el objeto Mensaje
        mensaje.setContenidoMensaje(mensajeGuardar.getContenidoMensaje());
        mensaje.setCoincidencia(coincidencia);
        mensaje.setRemitente(remitente);
        mensaje.setDestinatario(destinatario);

        // Guardar en la base de datos
        Mensaje mensajeGuardado = mensajeRepository.save(mensaje);

        // Convertir y devolver el DTO MensajeSalida
        return modelMapper.map(mensajeGuardado, MensajeSalida.class);
    }

    // Modificar un mensaje existente
    @Override
    public MensajeSalida modificarMensaje(MensajeModificar mensajeModificar) {
        Mensaje mensaje = mensajeRepository.findById(mensajeModificar.getId())
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

        // Modificar el contenido del mensaje
        mensaje.setContenidoMensaje(mensajeModificar.getContenidoMensaje());

        // Guardar el mensaje modificado
        Mensaje mensajeModificado = mensajeRepository.save(mensaje);

        // Convertir y devolver el DTO MensajeSalida
        return modelMapper.map(mensajeModificado, MensajeSalida.class);
    }

    // Obtener mensajes por coincidencia
    @Override
    public List<MensajeSalida> obtenerMensajesPorCoincidencia(Long idCoincidencia) {
        Coincidencia coincidencia = coincidenciaRepository.findById(idCoincidencia)
                .orElseThrow(() -> new RuntimeException("Coincidencia no encontrada"));

        List<Mensaje> mensajes = mensajeRepository.findByCoincidencia(coincidencia);

        return mensajes.stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.toList());
    }

    // Obtener mensajes enviados por un usuario
    @Override
    public List<MensajeSalida> obtenerMensajesEnviadosPorUsuario(Long idRemitente) {
        Usuario remitente = usuarioRepository.findById(idRemitente)
                .orElseThrow(() -> new RuntimeException("Remitente no encontrado"));

        List<Mensaje> mensajes = mensajeRepository.findByRemitente(remitente);

        return mensajes.stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.toList());
    }

    // Obtener mensajes recibidos por un usuario
    @Override
    public List<MensajeSalida> obtenerMensajesRecibidosPorUsuario(Long idDestinatario) {
        Usuario destinatario = usuarioRepository.findById(idDestinatario)
                .orElseThrow(() -> new RuntimeException("Destinatario no encontrado"));

        List<Mensaje> mensajes = mensajeRepository.findByDestinatario(destinatario);

        return mensajes.stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.toList());
    }
}
