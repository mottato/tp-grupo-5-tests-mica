package ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class EntidadDTO {
    private String codigo;
    private List<IncidenteDTO> incidentes;

    public EntidadDTO(String codigo) {
        this.codigo = codigo;
        this.incidentes = new ArrayList<>();
    }

    public void agregarIncidente(IncidenteDTO ... incidentes){ // esto se llama argumentos variables
        // me pueden llegar 0, 1, o muchos parametros(tienen que ser el mismo tipo)
        //puedo agregar 0 1 o muchos productos
        Collections.addAll(this.incidentes, incidentes);
    }
}
