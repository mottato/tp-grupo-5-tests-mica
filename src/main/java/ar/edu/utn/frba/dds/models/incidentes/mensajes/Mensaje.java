package ar.edu.utn.frba.dds.models.incidentes.mensajes;

import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;

import javax.persistence.criteria.CriteriaBuilder;

public interface Mensaje {
    public String contenido(IncidenteXComunidad incidenteXComunidad);
}
