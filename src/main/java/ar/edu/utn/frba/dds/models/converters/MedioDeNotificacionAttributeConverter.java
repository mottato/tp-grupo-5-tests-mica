package ar.edu.utn.frba.dds.models.converters;

import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapterImpl.NotificarPorEmail;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapterImpl.Twilio;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.sender.EmailSender;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.sender.WhatsappSender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class MedioDeNotificacionAttributeConverter implements AttributeConverter<MedioDeNotificacion, String> {
    @Override
    public String convertToDatabaseColumn(MedioDeNotificacion medioDeNotificacion) {
        String nombreDelMedio = "";

        switch (medioDeNotificacion.getClass().getSimpleName()) {
        case "WhatsappSender": nombreDelMedio = "wpp"; break;
        case "EmailSender": nombreDelMedio = "email"; break;
        default: nombreDelMedio = "desconocido"; break;
        }
        return nombreDelMedio;
        }
    @Override
    public MedioDeNotificacion convertToEntityAttribute(String s) {
        MedioDeNotificacion medio = null;

        if(Objects.equals(s, "wpp")){
            Twilio adapterWpp = new Twilio();
            medio = new WhatsappSender(adapterWpp);
        }

        if(Objects.equals(s, "email")) {

            NotificarPorEmail adapterEmail = new NotificarPorEmail();
            medio = new EmailSender(adapterEmail);
        }
        return medio;
    }
}
