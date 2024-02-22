package ar.edu.utn.frba.dds.models.incidentes;

import ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones.TurnoNotificacion;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
public class TipoNotificacion {
    private boolean inmediata;
    private List<TurnoNotificacion> preferenciaTurnos;


public void agregarNotificacion(String notificacion){
    for (TurnoNotificacion turno : preferenciaTurnos
         ) {
        turno.agregarNotificacion(notificacion);
    }
}


}


