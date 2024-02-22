package ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IncidenteDTO {
    private Integer tiempoRespuesta;
    private Boolean estaResuelto;
    private Integer miembrosAfectados;

    public IncidenteDTO(Integer tiempoRespuesta, Boolean estaResuelto, Integer miembrosAfectados) {
        this.tiempoRespuesta = tiempoRespuesta;
        this.estaResuelto = estaResuelto;
        this.miembrosAfectados = miembrosAfectados;
    }
}
