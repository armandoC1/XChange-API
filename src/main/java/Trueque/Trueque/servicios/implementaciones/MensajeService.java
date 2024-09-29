package Trueque.Trueque.servicios.implementaciones;

import Trueque.Trueque.dtos.mensaje.*;
import Trueque.Trueque.modelos.*;
import Trueque.Trueque.repositorios.*;
import Trueque.Trueque.servicios.interfaces.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MensajeService implements IMensajeService {

    @Autowired
    private IMensajeRepository mensajeRepository;

    @Autowired
    private IOfertaRepository ofertaRepository;

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MensajeSalida guardarMensaje(MensajeGuardar mensajeGuardarDTO) {
        Mensaje mensaje = new Mensaje();

        Optional<Oferta> ofertaOptional = ofertaRepository.findById(mensajeGuardarDTO.getIdOferta());
        Optional<Usuario> remitenteOptional = usuarioRepository.findById(mensajeGuardarDTO.getIdRemitente());
        Optional<Usuario> destinatarioOptional = usuarioRepository.findById(mensajeGuardarDTO.getIdDestinatario());

        if (ofertaOptional.isPresent() && remitenteOptional.isPresent() && destinatarioOptional.isPresent()) {
            Oferta oferta = ofertaOptional.get();
            Usuario remitente = remitenteOptional.get();
            Usuario destinatario = destinatarioOptional.get();

            mensaje.setContenidoMensaje(mensajeGuardarDTO.getContenidoMensaje());
            mensaje.setOferta(oferta);
            mensaje.setRemitente(remitente);
            mensaje.setDestinatario(destinatario);

            mensaje = mensajeRepository.save(mensaje);
            return modelMapper.map(mensaje, MensajeSalida.class);
        }
        return null;  // Manejar el caso de error adecuadamente
    }

    @Override
    public List<MensajeSalida> obtenerMensajesPorOferta(Long idOferta) {
        return mensajeRepository.findByOfertaIdOferta(idOferta)
                .stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<MensajeSalida> obtenerMensajesEntreUsuarios(Long idOferta, Long idRemitente, Long idDestinatario) {
        return mensajeRepository.findByOfertaIdOfertaAndRemitenteIdUsuarioAndDestinatarioIdUsuario(idOferta, idRemitente, idDestinatario)
                .stream()
                .map(mensaje -> modelMapper.map(mensaje, MensajeSalida.class))
                .collect(Collectors.toList());
    }
}
