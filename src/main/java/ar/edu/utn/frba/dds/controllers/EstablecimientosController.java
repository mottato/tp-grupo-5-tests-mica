package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.entidades.Establecimiento;
import ar.edu.utn.frba.dds.models.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeProvincias;
import ar.edu.utn.frba.dds.models.georef.entities.Provincia;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEstablecimientos;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class EstablecimientosController extends Controller implements ICrudViewsHandler {

    private RepositorioDeEstablecimientos repositorioDeEstablecimientos;

    private EntidadesController entidadesController = new EntidadesController(new RepositorioDeEntidades());

    private RepositorioDeEntidades repositorioDeEntidades;

    ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();

    public EstablecimientosController(RepositorioDeEstablecimientos repositorioDeEstablecimientos, RepositorioDeEntidades repositorioDeEntidades) {
        this.repositorioDeEstablecimientos = repositorioDeEstablecimientos;
        this.repositorioDeEntidades = repositorioDeEntidades;
    }

    @Override
    public void index(Context context) {

        Map<String, Object> model = new HashMap<>();
        List<Entidad> establecimientos = this.repositorioDeEstablecimientos.buscarTodos();
        model.put("establecimientos", establecimientos);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("establecimientos/establecientos.hbs", model);

    }

    @Override
    public void show(Context context) {

        //context vas a tener el id

        //query usando el id

        //retornas la vista hidratada con el query

    }

    @Override
    public void create(Context context) throws IOException {

        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("idEntidad")));
        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setEntidad(entidad);
        establecimiento.setIdProvincia(2L);
        repositorioDeEstablecimientos.guardar(establecimiento);
        /*
        ListadoDeMunicipios listadoDeMunicipios = new ListadoDeMunicipios();
        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();

        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        model.put("provincias", listadoDeProvincias.provincias);
        model.put("municipios", listadoDeMunicipios.municipios);
        model.put("entidad", entidad);
        this.cargarVariablesSesion(context,model);
        */
        //por defecto se crean en BS AS
        context.redirect("/establecimientos/"+establecimiento.getId().toString()+"/2/editar/%20");

    }

    //deprecado
    public void cargarMunicipios(Context context) throws IOException {

        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("idEntidad")));
     //   Establecimiento establecimiento = null;


        ListadoDeMunicipios listadoDeMunicipios = servicioGeoref.listadoDeMunicipiosDeProvincia(Integer.parseInt(context.pathParam("idProvincia")));
        ListadoDeProvincias listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(context.pathParam("idProvincia")));
        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();

        Map<String, Object> model = new HashMap<>();

        model.put("leyenda", context.pathParam("leyenda"));
        model.put("provincia", listadoDeProvinciasPorID.provincias.get(0));


        model.put("municipios", listadoDeMunicipios.municipios);
       // model.put("establecimiento", establecimiento);
        model.put("provincias", listadoDeProvincias.provincias);
        model.put("municipios", listadoDeMunicipios.municipios);
        model.put("entidad", entidad);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("establecimientos/establecimiento.hbs", model);
    }

    @Override
    public void save(Context context) {

        Establecimiento establecimiento = new Establecimiento();
        this.asignarParametros(establecimiento, context);
        this.repositorioDeEstablecimientos.guardar(establecimiento);
        context.status(HttpStatus.CREATED);
        /*Map<String, Object> model = new HashMap<>();
        model.put("entidad", establecimiento.getEntidad());
        model.put("establecimientos", establecimiento.getEntidad().misEstablecimientos());*/
        //context.render("establecimientos/establecimientos.hbs", model);
        context.redirect("entidades/"+establecimiento.getEntidad().getId()+"/establecimientos");

    }

    @Override
    public void edit(Context context) throws IOException {

        Establecimiento establecimiento = (Establecimiento) this.repositorioDeEstablecimientos.buscar(Long.parseLong(context.pathParam("id")));
        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();

        context.status(HttpStatus.OK);
        Map<String, Object> model = new HashMap<>();

        //carga el selected
        if(!Objects.equals(context.pathParam("idProvincia"), "")) {
            ListadoDeProvincias listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(context.pathParam("idProvincia")));
            model.put("provincia", listadoDeProvinciasPorID.provincias.get(0));
        }

        ListadoDeMunicipios listadoDeMunicipios=null;
        //carga todos los municipios que usa
        if(!Objects.equals(context.pathParam("idProvincia"), "")) {
            listadoDeMunicipios = servicioGeoref.listadoDeMunicipiosDeProvincia(Integer.parseInt(context.pathParam("idProvincia")));
        }

        if(!Objects.equals(context.formParam("idMunicipio"), null)) {
            establecimiento.setIdMunicipio(Integer.parseInt(context.formParam("idMunicipio")));
        }

        //carga el selected de municipio
        if(!Objects.equals(establecimiento.getIdMunicipio(), 0)) {
            ListadoDeMunicipios listadoDeMunicipiosPorID = servicioGeoref.listadoDeMunicipiosPorID(establecimiento.getIdMunicipio());
            model.put("municipio", listadoDeMunicipiosPorID.municipios.get(0));
            /*
            ListadoDeProvincias listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(establecimiento.getIdProvincia().toString()));
            model.put("municipio", listadoDeProvinciasPorID.provincias.get(0));
        */
        }

        model.put("leyenda", context.pathParam("leyenda"));

        model.put("municipios", listadoDeMunicipios.municipios);
        model.put("provincias", listadoDeProvincias.provincias);
        model.put("establecimiento", establecimiento);
        model.put("entidad", establecimiento.getEntidad());

        this.cargarVariablesSesion(context, model);

        context.render("establecimientos/establecimiento.hbs", model);
    }

    @Override
    public void update(Context context) {
        Establecimiento establecimiento = (Establecimiento) this.repositorioDeEstablecimientos.buscar(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(establecimiento, context);
        this.repositorioDeEstablecimientos.actualizar(establecimiento);
        context.status(HttpStatus.OK);
        context.redirect("/entidades/"+establecimiento.getEntidad().getId().toString()+"/establecimientos");
    }


    @Override
    public void delete(Context context) {

    }

    private void asignarParametros(Establecimiento establecimiento, Context context) {
        if(!Objects.equals(context.formParam("leyenda"), "")) {
            establecimiento.setLeyenda(context.formParam("leyenda"));
            establecimiento.setIdProvincia(Long.parseLong(context.formParam("idProvincia")));
            establecimiento.setIdMunicipio(Integer.parseInt(context.formParam("idMunicipio")));
          }
    }

    public void showServicios(Context context){
        Establecimiento establecimiento = (Establecimiento) this.repositorioDeEstablecimientos.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", establecimiento);
        model.put("servicios", establecimiento.getServicios());
        this.cargarVariablesSesion(context, model);
        context.render("servicios/servicios.hbs", model);

    }

}
