package ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapter;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import org.apache.commons.mail.EmailException;

public interface AdapterEmail {

    void enviarNotificacion(Miembro miembro, String notificacio) throws EmailException;
}
