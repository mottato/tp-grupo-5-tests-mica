package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EntidadesController extends Controller implements ICrudViewsHandler {

    private RepositorioDeEntidades repositorioDeEntidades;

    public EntidadesController(RepositorioDeEntidades repositorioDeEntidades) {
        this.repositorioDeEntidades = repositorioDeEntidades;
    }

    @Override
    public void index(Context context) {
        Map<String, Object> model = new HashMap<>();
        List<Entidad> entidades = this.repositorioDeEntidades.buscarTodos();
        model.put("entidades", entidades);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("entidades/entidades.hbs", model);
    }

    @Override
    public void show(Context context) {

        //context vas a tener el id

        //query usando el id

        //retornas la vista hidratada con el query

    }

    @Override
    public void create(Context context) {
        Entidad entidad = null;
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));

        model.put("id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));

        context.render("entidades/entidad.hbs", model);
    }

    @Override
    public void save(Context context) {
        Entidad entidad = new Entidad();
        this.asignarParametros(entidad, context);
        this.repositorioDeEntidades.guardar(entidad);
        context.status(HttpStatus.CREATED);
        context.redirect("/entidades");
    }

    @Override
    public void edit(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("entidades/entidad.hbs", model);
    }

    public Entidad encontrar(Long id){
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(id);
        return entidad;
    }

    @Override
    public void update(Context context) {
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        this.asignarParametros(entidad, context);
        this.repositorioDeEntidades.actualizar(entidad);
        context.redirect("/entidades");
    }

    @Override
    public void delete(Context context) {

    }

    public void showEstablecimientos(Context context){
        Entidad entidad = (Entidad) this.repositorioDeEntidades.buscar(Long.parseLong(context.pathParam("id")));
        Map<String, Object> model = new HashMap<>();
        model.put("entidad", entidad);
        model.put("establecimientos", entidad.misEstablecimientos());
        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id", context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        context.render("establecimientos/establecimientos.hbs", model);

    }

    public void cargaMasiva(Context context){
        Map<String, Object> model = new HashMap<>();
        this.cargarVariablesSesion(context,model);
        context.render("carga_masiva/carga-masiva.hbs", model);
    }

    private void asignarParametros(Entidad entidad, Context context) {
        if(!Objects.equals(context.formParam("leyenda"), "")) {
            entidad.setLeyenda(context.formParam("leyenda"));
        }
    }

}
