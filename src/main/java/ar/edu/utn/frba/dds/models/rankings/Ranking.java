package ar.edu.utn.frba.dds.models.rankings;

import ar.edu.utn.frba.dds.models.entidades.Entidad;

import java.util.List;

public class Ranking {
    // definir que sera un ranking, una plantilla...s
    public Ranking(List<Entidad> entidades, String criterio){

        System.out.println("Ranking generado por criterio "+criterio+" : ");
        for (Entidad entidad: entidades
             ) {
            System.out.println(entidad.getLeyenda());
        }

    }
}
