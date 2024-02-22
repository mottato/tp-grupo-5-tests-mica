package ar.edu.utn.frba.dds.models.incidentes.mensajes;

import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;

public class CierreIncidente implements Mensaje {

    public CierreIncidente(){

    }

    public String contenido(IncidenteXComunidad incidenteXComunidad){

        return "Se cerró el incidente en el servicio "+incidenteXComunidad.getIncidente().getServicio().getNombre()+"a las "
                +incidenteXComunidad.getFechaCierre().toString()+" con las siguientes observaciones: "+incidenteXComunidad.getObservacionesCierre();

    }
}
