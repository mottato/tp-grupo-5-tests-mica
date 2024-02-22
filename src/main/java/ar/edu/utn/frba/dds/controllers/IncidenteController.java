package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;
import ar.edu.utn.frba.dds.models.repositorios.*;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.apache.commons.mail.EmailException;

import java.util.*;
import java.util.stream.Collectors;

public class IncidenteController extends Controller implements ICrudViewsHandler {

    private RepositorioDeIncidentes repositorioDeIncidentes;
    private RepositorioDeServicios repositorioServicios = new RepositorioDeServicios();

    private RepositorioDeUsuarios repositorioDeUsuarios = new RepositorioDeUsuarios();

    private RepositorioDeRoles repositorioDeRoles = new RepositorioDeRoles();

    private RepositorioDeIncidentesXComunidad repositorioDeIncidentesXComunidad = new RepositorioDeIncidentesXComunidad();

    public IncidenteController(RepositorioDeIncidentes repositorioDeIncidentes){
        this.repositorioDeIncidentes = new RepositorioDeIncidentes();

    }
    @Override
    public void index(Context context) {


        List<Servicio> servicios= this.repositorioServicios.buscarTodos();
        Map<String, Object> model = new HashMap<>();
        model.put("servicios", servicios);
        this.cargarVariablesSesion(context,model);
        context.render("apertura_incidentes/apertura-incidentes.hbs", model);

    }

    @Override
    public void show(Context context) {
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscar(Long.parseLong(context.sessionAttribute("usuario_id")));
        Miembro miembro = (Miembro)  usuario.getRol();

        List<Incidente> incidentes= this.repositorioDeIncidentes.buscarTodos();
        List<Incidente> incidentesAbiertos = incidentes
                .stream()
                .filter(incidente -> incidente.getEstado())
                .collect(Collectors.toList());
        List<Incidente> incidentesCerrados = incidentes
                .stream()
                .filter(incidente -> !incidente.getEstado())
                .collect(Collectors.toList());

        List<Incidente> incidentesXCerrar = new ArrayList<>();
        incidentesAbiertos.forEach(incidente -> {

            List<IncidenteXComunidad> incidenteXComunidads = (List<IncidenteXComunidad>) this.repositorioDeIncidentesXComunidad.buscarXIncidente(incidente.getId());

            Boolean busqueda = incidenteXComunidads.stream().anyMatch(
                    incidenteXComunidad ->
                        miembro.getComunidades().contains(incidenteXComunidad.getComunidad()));

            if(busqueda){
                incidentesXCerrar.add(incidente);
            }

        });

        incidentesAbiertos = incidentesAbiertos
                .stream()
                .filter(incidente -> !incidentesXCerrar.contains(incidente))
                .collect(Collectors.toList());

        Map<String, Object> model = new HashMap<>();
        model.put("incidentesXCerrar", incidentesXCerrar);
        model.put("incidentes", incidentesAbiertos);
        model.put("incidentesCerrados", incidentesCerrados);
        this.cargarVariablesSesion(context,model);
        context.render("incidentes/incidentes.hbs", model);

    }

    @Override
    public void create(Context context) {

     /*   Incidente incidente = new Incidente();
        Map<String, Object> model = new HashMap<>();
        model.put("incidente", incidente);

        context.render("apertura_incidentes/apertura-incidentes.hbs");*/
    }

    @Override
    public void save(Context context) {
        //GUARDAR EL RECURSO

        Usuario usuario = (Usuario) repositorioDeUsuarios.buscar(Long.parseLong(context.sessionAttribute("usuario_id")));

        Miembro miembro = (Miembro)  usuario.getRol();

        Servicio servicio = (Servicio) repositorioServicios.buscar(Long.parseLong(context.formParam("servicio_id")));

        String observaciones = context.formParam("observaciones");

        Incidente incidente = miembro.abrirIncidente(servicio, observaciones);
        this.repositorioDeIncidentes.guardar(incidente);

        List<IncidenteXComunidad> incidentes = miembro.incidentesXComunidad(incidente, miembro.getComunidades());

        incidentes.forEach( incidenteXComunidad -> {
                    this.repositorioDeIncidentesXComunidad.guardar(incidenteXComunidad);
                }
        );

        context.status(HttpStatus.CREATED);
        context.redirect("/incidentes");

    }

    @Override
    public void edit(Context context) {

    }

    @Override
    public void update(Context context) {

    }

    @Override
    public void delete(Context context) {

    }

    public void cerrar(Context context){

        Incidente incidente= (Incidente) this.repositorioDeIncidentes.buscar(Long.parseLong(context.pathParam("incidente_id")));
        Map<String, Object> model = new HashMap<>();
        //Miembro miembro = (Miembro) this.repositorioDeRoles.buscar(incidente.getAbiertoPor());
        model.put("incidente", incidente);
        this.cargarVariablesSesion(context,model);
        context.render("cierre_incidentes/cierre-incidentes.hbs", model);

    }

    public void cerrando(Context context) throws EmailException {
        Incidente incidente= (Incidente) this.repositorioDeIncidentes.buscar(Long.parseLong(context.formParam("incidente")));
        Miembro miembro = (Miembro) this.repositorioDeRoles.buscar(Long.parseLong(context.formParam("miembro")));
        String observaciones = context.formParam("observaciones");

        incidente.cerrar();

        this.repositorioDeIncidentes.actualizar(incidente);

        List<IncidenteXComunidad> incidentesXComunidads = (List<IncidenteXComunidad>) this.repositorioDeIncidentesXComunidad.buscarXIncidente(incidente.getId());

        incidentesXComunidads.forEach( incidenteXComunidad -> {

                    if(miembro.getComunidades().contains(incidenteXComunidad.getComunidad())){
                        try {
                            incidenteXComunidad.cerrar(observaciones, miembro);
                        } catch (EmailException e) {
                            throw new RuntimeException(e);
                        }
                        this.repositorioDeIncidentesXComunidad.guardar(incidenteXComunidad);
                    }
                }
        );

        Map<String, Object> model = new HashMap<>();
        this.cargarVariablesSesion(context,model);
        model.put("servicio", incidente.getServicio().getNombre());
        context.render("cierre_incidentes/cierre-exitoso.hbs", model);
    }

    private void asignarParametros(Incidente incidente, Context context) {
        if(!Objects.equals(context.formParam("observaciones"), "")) {
            incidente.setObservacionesApertura(context.formParam("observaciones"));
            Servicio servicio = (Servicio) repositorioServicios.buscar(Long.parseLong(context.formParam("servicio_id")));
            incidente.setServicio(servicio);
        }
    }

}

