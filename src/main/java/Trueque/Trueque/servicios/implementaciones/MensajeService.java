package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.seguridad.modelos.Usuario;
import Trueque.Trueque.dtos.mensaje.*;
import Trueque.Trueque.modelos.*;
import Trueque.Trueque.repositorios.*;
import Trueque.Trueque.seguridad.repositorios.UsuarioRepository;
import Trueque.Trueque.servicios.interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MensajeService implements IMensajeService {

    @Autowired
    private IMensajeRepository mensajeRepository;

    @Autowired
    private IOfertaRepository ofertaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MensajeSalida guardarMensaje(MensajeGuardar mensajeGuardarDTO) {
        Mensaje mensaje = new Mensaje();

        Optional<Usuario> remitenteOptional = usuarioRepository.findById(mensajeGuardarDTO.getIdRemitente());
        Optional<Usuario> destinatarioOptional = usuarioRepository.findById(mensajeGuardarDTO.getIdDestinatario());

        if (remitenteOptional.isPresent() && destinatarioOptional.isPresent()) {
            Usuario remitente = remitenteOptional.get();
            Usuario destinatario = destinatarioOptional.get();

            mensaje.setContenidoMensaje(mensajeGuardarDTO.getContenidoMensaje());
            mensaje.setRemitente(remitente);
            mensaje.setDestinatario(destinatario);

            mensaje = mensajeRepository.save(mensaje);
            return modelMapper.map(mensaje, MensajeSalida.class);
        }
        return null;
    }


    @Override
    public List<MensajeSalida> obtenerMensajesPorOferta(Long idOferta) {
        return mensajeRepository.findByOfertaIdOferta(idOferta)
                .stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, Map<Long, List<MensajeSalida>>> obtenerMensajesAgrupados(Long userId) {
        List<Mensaje> mensajes = mensajeRepository.findMensajesPorUsuario(userId);

        return mensajes.stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.groupingBy(
                        MensajeSalida::getIdDestinatario,
                        Collectors.groupingBy(MensajeSalida::getIdRemitente)
                ));
    }

}
