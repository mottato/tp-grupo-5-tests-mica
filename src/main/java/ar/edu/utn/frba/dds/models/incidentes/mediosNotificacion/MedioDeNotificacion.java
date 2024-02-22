package ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import org.apache.commons.mail.EmailException;

import javax.persistence.*;

public interface MedioDeNotificacion {

    void enviarNotificacion(Miembro miembro, String notificacion) throws EmailException;
}
