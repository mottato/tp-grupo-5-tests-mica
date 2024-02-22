package ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ExternoResponse {
    private List<EntidadResultDTO> entidades;

    public ExternoResponse() {
        this.entidades = new ArrayList<>();
    }

    public void agregarEntidad(EntidadResultDTO serviceResultDTO) {
        entidades.add(serviceResultDTO);
    }

    public void ordenarPorImpacto() {
        entidades.sort((entidad1, entidad2) -> Integer.compare(entidad2.getImpactoIncidentes(), entidad1.getImpactoIncidentes()));
    }
}
