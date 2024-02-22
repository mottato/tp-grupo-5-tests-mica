package ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Getter
@Setter
public class ExternoRequest {
    private List<EntidadDTO> entidades;

    public ExternoRequest() {
        this.entidades = new ArrayList<>();
    }

    public void agregarEntidad(EntidadDTO ... entidades){
        Collections.addAll(this.entidades, entidades);
    }}
