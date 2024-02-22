package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entidades.Empresa;
import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.entidades.Establecimiento;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEmpresas;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEstablecimientos;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeServicios;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.*;


public class ServiciosController extends Controller implements ICrudViewsHandler {

    private RepositorioDeServicios repositorioDeServicios;

    private RepositorioDeEmpresas repositorioDeEmpresas = new RepositorioDeEmpresas();;

    private RepositorioDeEstablecimientos repositorioDeEstablecimientos = new RepositorioDeEstablecimientos();
    public ServiciosController(RepositorioDeServicios repositorioDeServicios){
        this.repositorioDeServicios = repositorioDeServicios;
    }

    @Override
    public void index(Context context) {

        Map<String, Object> model = new HashMap<>(); /*
        List<Entidad> establecimientos = this.repositorioDeEstablecimientos.buscarTodos();
        model.put("establecimientos", establecimientos); */
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("servicios/servicios.hbs", model);

    }

    @Override
    public void show(Context context) {

        //context vas a tener el id

        //query usando el id

        //retornas la vista hidratada con el query

    }

    @Override
    public void create(Context context) {

        Establecimiento establecimiento = (Establecimiento) this.repositorioDeEstablecimientos.buscar(Long.parseLong(context.pathParam("idEstablecimiento")));
        Servicio servicio = null;
        Map<String, Object> model = new HashMap<>();
        List<Empresa> empresas =  this.repositorioDeEmpresas.buscarTodos();
        model.put("establecimiento", establecimiento);
        model.put("servicio", servicio);
        model.put("empresas", empresas);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("servicios/servicio.hbs", model);
    }

    @Override
    public void save(Context context) {

        Servicio servicio = new Servicio();
        this.asignarParametros(servicio, context);
        this.repositorioDeServicios.guardar(servicio);
        context.status(HttpStatus.CREATED); /*
        Map<String, Object> model = new HashMap<>();
        model.put("establecimiento", servicio.getEstablecimiento());
        model.put("servicios", servicio.getEstablecimiento().getServicios());
        context.render("servicios/servicios.hbs", model); */
        context.redirect("/establecimientos/"+servicio.getEstablecimiento().getId()+"/servicios");
    }

    @Override
    public void edit(Context context) {

        Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        List<Empresa> empresas = this.repositorioDeEmpresas.buscarTodos();
        model.put("servicio", servicio);
        model.put("establecimiento", servicio.getEstablecimiento());
        model.put("empresas",empresas);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("servicios/servicio.hbs", model);
    }

    @Override
    public void update(Context context) {
        Servicio servicio = (Servicio) this.repositorioDeServicios.buscar(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(servicio, context);
        this.repositorioDeServicios.actualizar(servicio);
        /*
        Map<String, Object> model = new HashMap<>();

        model.put("establecimiento", servicio.getEstablecimiento());
        model.put("servicios", servicio.getEstablecimiento().getServicios());
        context.render("servicios/servicios.hbs", model); */
        context.redirect("/establecimientos/"+servicio.getEstablecimiento().getId()+"/servicios");
    }

    @Override
    public void delete(Context context) {

    }

    private void asignarParametros(Servicio servicio, Context context) {
        if(!Objects.equals(context.formParam("nombre"), "")) {
            servicio.setNombre(context.formParam("nombre"));
            servicio.setCodigoServicio(Integer.parseInt(context.formParam("codigoServicio")));
            Establecimiento establecimiento = (Establecimiento) this.repositorioDeEstablecimientos.buscar(Long.parseLong(context.formParam("idEstablecimiento")));
            servicio.setEstablecimiento(establecimiento);
            servicio.setEstaActivo(Boolean.parseBoolean(context.formParam("estaActivo")));
            servicio.setEsDeElevacion(Boolean.parseBoolean(context.formParam("esDeElevacion")));
            Empresa empresa = (Empresa) this.repositorioDeEmpresas.buscar(Long.parseLong(context.formParam("idEmpresa")));
            servicio.setEmpresa(empresa);
        }
    }


}
