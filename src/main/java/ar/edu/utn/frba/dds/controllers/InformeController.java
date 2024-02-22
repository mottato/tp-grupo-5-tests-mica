package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.converters.LocalDateTimeAttributeConverter;
import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;
import ar.edu.utn.frba.dds.models.rankings.RankingXTiempo;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeIncidentes;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeIncidentesXComunidad;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class InformeController extends Controller implements ICrudViewsHandler {


    private RepositorioDeIncidentes repositorioDeIncidentes = new RepositorioDeIncidentes();
    private RepositorioDeIncidentesXComunidad repositorioDeIncidentesXComunidad = new RepositorioDeIncidentesXComunidad();

    public class XTiempo{
        public Entidad entidad;
        public Long segundosAcumulados;
        public int cantidadIncidentes;
    }
    @Getter
    @Setter
    public class entidadXPromedio{
        public Entidad entidad;
        public Long promedio;
    }

    private List<XTiempo> reporteXTiempo = new ArrayList<>();
    private List<entidadXPromedio> entidadXPromedios = new ArrayList<>();

    public long segundosEntreFechas(LocalDateTime apertura, LocalDateTime cierre){

        return  ChronoUnit.SECONDS.between(apertura, cierre);
    }


    public void calcularPromedio(){
        this.reporteXTiempo.forEach(
                reporte -> {
                    entidadXPromedio entidadXPromedio = new entidadXPromedio();
                    entidadXPromedio.entidad=reporte.entidad;
                    entidadXPromedio.promedio=reporte.segundosAcumulados/reporte.cantidadIncidentes;
                    this.entidadXPromedios.add(entidadXPromedio);
                }
        );
    }


    @Override
    public void index(Context context) {

    }

    @Override
    public void show(Context context) {
        Map<String, Object> model = new HashMap<>();
        model.put("servicio","Molinete");
        model.put("establecimiento","Molinete-Medrano");
        model.put("cantidadIncidentes",10);
        this.cargarVariablesSesion(context,model);
        context.render("informes/generar-informes-incidentes.hbs", model);

    }

    public void agregarIncidente(Incidente incidente, IncidenteXComunidad incidenteXComunidad){

        Entidad entidad = incidente.getServicio().getEstablecimiento().getEntidad();
        Long segundosAcumulados = this.segundosEntreFechas(incidente.getFechaApertura(),incidenteXComunidad.getFechaCierre());

        XTiempo xTiempo = new XTiempo();

        boolean encontro = this.reporteXTiempo.stream().anyMatch(elemento -> Objects.equals(elemento.entidad, entidad));

        //si la entidad no esta en la lista la agrega
        if(encontro){
            int index=-1;
            boolean end=false;
            while(!end || index>this.reporteXTiempo.size()){
                index++;
                if(this.reporteXTiempo.get(index).entidad.equals(entidad)){
                    end=true;
                }
            }
            xTiempo=this.reporteXTiempo.get(index);
            xTiempo.segundosAcumulados+=segundosAcumulados;
            xTiempo.cantidadIncidentes+=1;
            this.reporteXTiempo.set(index, xTiempo);
        }else{
            xTiempo.cantidadIncidentes=1;
            xTiempo.entidad=entidad;
            xTiempo.segundosAcumulados=segundosAcumulados;
            this.reporteXTiempo.add(xTiempo);
        }
    }


    @Override
    public void create(Context context) throws Exception {

        /*
        entidades con mayor tiempo promedio de tiempo de cierre de incidentes (diferencia entre horario de
        cierre de incidente y horario de apertura) en la semana. Este ranking es orientativo y puede no ser
        la tasa real de corrección de las fallas;
        */

        LocalDateTime desde=LocalDateTime.parse("2024-01-01T00:00:00");
        LocalDateTime hasta=LocalDateTime.now();

        List<Long> incidentesCerrados=null;
        try {
            incidentesCerrados = (List<Long>) this.repositorioDeIncidentes.buscarCerrados(desde, hasta);
        }catch (Exception e){
            System.out.println("error consultando");
        }

        List<Incidente> incidentes= new ArrayList<>();


        incidentesCerrados.forEach(
                incidenteC ->{
                    Incidente incidente = (Incidente) this.repositorioDeIncidentes.buscar(incidenteC);
                    List<IncidenteXComunidad> incidenteXComunidad = (List<IncidenteXComunidad>) this.repositorioDeIncidentesXComunidad.buscaPrimerCierre(incidenteC);
                    this.agregarIncidente(incidente, incidenteXComunidad.get(0));
                }
        );

        this.calcularPromedio();


        Map<String, Object> model = new HashMap<>();
        this.cargarVariablesSesion(context,model);

        model.put("entidades", entidadXPromedios.stream()
                .sorted(Comparator.comparingLong(entidadXPromedio::getPromedio))
                .toList());
        context.render("informes/generar-informes-incidentes.hbs", model);

        /*
        entidades con mayor cantidad de incidentes reportados en la semana. Una vez que un incidente
        sobre un servicio es reportado por algún usuario, independientemente de la comunidad de la que
        forma parte, no se consideran, para el presente ranking, ningún incidente que se genere sobre
        dicho servicio en un plazo de 24 horas siempre y cuando el mismo continúe abierto (un incidente
        reportado como cerrado anula el plazo sobre el servicio y el siguiente incidente sí se considera para
        el cálculo);
         */

        /*
        mayor grado de impacto de las problemáticas considerando que las que algunas comunidades
        tienen mayor cantidad de miembros y por lo tanto les afecta de mayor medida el no funcionamiento
        de ese servicio. El detalle de la generación de ranking con este criterio será considerado en la
        siguiente entrega.
        */
    }

    @Override
    public void save(Context context) throws IOException {

    }

    @Override
    public void edit(Context context) throws IOException {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }
}
