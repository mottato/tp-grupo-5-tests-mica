package ar.edu.utn.frba.dds.models.rankings;

import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.incidentes.Incidente;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class CalculadorPromedioCierre {
    private List<Incidente> incidentes;
    private  Long segundosAcumulados;

    private int cantidadIncidentes;
    public Long promedio(Entidad entidad, LocalDateTime reporteDesde, LocalDateTime reporteHasta){

         incidentes = entidad.listarIncidentes(reporteDesde, reporteHasta);

         for (Incidente incidente : incidentes
        ) {
            // x cada incidente se calcula el cierre

            //repensar linea 24 x los cambios hechos
            segundosAcumulados += this.segundosEntreFechas(incidente.getFechaApertura(), incidente.getFechaApertura());
            cantidadIncidentes += 1;
        }

        return segundosAcumulados/cantidadIncidentes;
    }

    public long segundosEntreFechas(LocalDateTime apertura, LocalDateTime cierre){

        return  apertura.toEpochSecond(ZoneOffset.of("-03:00"))-cierre.toEpochSecond(ZoneOffset.of("-3:00"));
    }


}
