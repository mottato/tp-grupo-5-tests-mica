package ar.edu.utn.frba.dds.models.rankings;

import ar.edu.utn.frba.dds.models.entidades.Entidad;

import java.util.List;

public interface IRankeable {
    Ranking rankear(List<Entidad> entidad);
}
