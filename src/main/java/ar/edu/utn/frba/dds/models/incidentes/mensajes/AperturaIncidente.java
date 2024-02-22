package ar.edu.utn.frba.dds.models.incidentes.mensajes;

import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;

public class AperturaIncidente implements Mensaje {

    public AperturaIncidente(){

    }

    public String contenido(IncidenteXComunidad incidenteXComunidad){

        return "Se abrió un incidente en el servicio "+incidenteXComunidad.getIncidente().getServicio().getNombre()+"a las "
                +incidenteXComunidad.getIncidente().getFechaApertura().toString()+" con las siguientes observaciones: "+incidenteXComunidad.getIncidente().getObservacionesApertura();

    }

}
