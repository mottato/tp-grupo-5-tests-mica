package ar.edu.utn.frba.dds.models.rankings;

import ar.edu.utn.frba.dds.models.entidades.Entidad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RankingXTiempo implements IRankeable {

    private LocalDateTime reporteDesde;
    private LocalDateTime reportehasta;

    private CalculadorPromedioCierre calculadorPromedioCierre;

    //clase auxiliar para ordenar listado
    @Getter
    @Setter
    public class EntidadConSegundos{
        private Entidad entidad; // quedarse con el ID de la entidad solamente
        private Long promedioCierre;
    }

    private List<EntidadConSegundos> segundosxEntidades = new ArrayList<>();
    private EntidadConSegundos reporteEntidad;
    private List<Entidad> entidadesRankeadas= new ArrayList<>();
    private Entidad entidad;
    @Override
    public Ranking rankear(List<Entidad> entidades) {
        for (Entidad entidad: entidades
             ) {
            reporteEntidad.entidad=entidad;
            //uso calculador de promedio
            reporteEntidad.promedioCierre = calculadorPromedioCierre.promedio(entidad, reporteDesde, reportehasta);
            segundosxEntidades.add(reporteEntidad);
        }

        // finalmente se ordena la lista para obtener el ranking
        List<EntidadConSegundos> entidadesOrdenadas =
                segundosxEntidades.stream()
                                  .sorted(Comparator.comparingLong(EntidadConSegundos::getPromedioCierre))
                                  .toList();

        //solo me quedo con las entidades
        for (EntidadConSegundos EntidadxSegundos: entidadesOrdenadas
             ) {
            entidad = EntidadxSegundos.getEntidad();
            entidadesRankeadas.add(entidad);
        }

        return new Ranking(entidadesRankeadas, "mayor promedio de tiempo de cierre de incidentes");

    }


}
