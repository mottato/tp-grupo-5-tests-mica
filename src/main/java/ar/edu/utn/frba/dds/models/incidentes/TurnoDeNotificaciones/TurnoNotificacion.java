package ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public abstract class TurnoNotificacion {
    protected List<Miembro> miembros = new ArrayList<>();
    protected List<String> notificaciones = new ArrayList<>();

    public void agregarNotificacion(String notificacion){
        notificaciones.add(notificacion);
    }

    public void quitarNotificacion(String notificacion){
        notificaciones.remove(notificacion);
    }

    public void agregarMiembro(Miembro miembro){
        miembros.add(miembro);
    }
}
