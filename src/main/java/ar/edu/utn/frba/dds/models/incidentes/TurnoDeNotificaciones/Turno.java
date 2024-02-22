package ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;

import java.util.HashMap;

public class Turno {

    private HashMap<Miembro, String> notificaciones;

    public Turno(){
        this.notificaciones= new HashMap<>();
    }
    public HashMap<Miembro, String> getNotificaciones(){
        return notificaciones;
    }

    public void reset(){
        notificaciones=new HashMap<>();
    }

    public void agregarNotificacion(Miembro miembro, String notificacion){
        notificaciones.put(miembro, notificacion);
    }

    public void quitarNotificacion(Miembro miembro, String notificacion){
        notificaciones.remove(miembro, notificacion);
    }

}
