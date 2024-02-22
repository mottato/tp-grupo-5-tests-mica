package ar.edu.utn.frba.dds.models.incidentes;

import ar.edu.utn.frba.dds.models.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones.Turno;
import ar.edu.utn.frba.dds.models.incidentes.mensajes.Mensaje;
import org.apache.commons.mail.EmailException;

import java.util.HashMap;

public class Notificador {

    public static Turno turno;

    public Notificador(Turno turno){
        this.turno=turno;
    }

    public void notificar(Comunidad comunidad, IncidenteXComunidad incidente, Mensaje mensaje, Turno turno) throws EmailException {

        //Arma mensaje
        String notificacion = mensaje.contenido(incidente);

        for (Miembro miembro :  comunidad.getMiembros()
             ) {

            if(miembro.getNotificacionInmediata()){
                // se notifica de one
                miembro.getMedioDeNotificacionPreferido().enviarNotificacion(miembro, notificacion);
            }
            else{
                //se agrega al turno de notificacion
                Notificador.turno.agregarNotificacion(miembro, notificacion);
            }

        }

    }

    public void notificarPendientes(Turno turno) throws EmailException {

        HashMap<Miembro, String> notificaciones = Notificador.turno.getNotificaciones();

        notificaciones.forEach((miembro,mensaje) -> {
            try {
                miembro.getMedioDeNotificacionPreferido().enviarNotificacion(miembro, mensaje);
            } catch (EmailException e) {
                throw new RuntimeException(e);
            }
        });

        Notificador.turno.reset();

    }

}
