package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.comunidades.RolesUsuario;
import ar.edu.utn.frba.dds.models.comunidades.TipoRol;
import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import ar.edu.utn.frba.dds.models.converters.MedioDeNotificacionAttributeConverter;
import ar.edu.utn.frba.dds.models.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeProvincias;
import ar.edu.utn.frba.dds.models.georef.entities.Municipio;
import ar.edu.utn.frba.dds.models.georef.entities.Provincia;
import ar.edu.utn.frba.dds.models.comunidades.*;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapterImpl.NotificarPorEmail;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapterImpl.Twilio;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.sender.EmailSender;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.sender.WhatsappSender;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeRoles;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.*;

public class UsuarioController extends Controller implements ICrudViewsHandler {

    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeRoles repositorioDeRoles = new RepositorioDeRoles();

    ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();

    public UsuarioController(RepositorioDeUsuarios repositorioDeUsuarios) {
        this.repositorioDeUsuarios = repositorioDeUsuarios;
    }
    @Override
    public void index(Context context) {

       // context.render("administracion_tipos_usuarios/administrar-usuarios.hbs");
    }

    public void perfil(Context context) throws IOException {

        Usuario usuario = (Usuario) repositorioDeUsuarios.buscar(Long.parseLong(context.sessionAttribute("usuario_id")));
        Miembro miembro = (Miembro) usuario.getRol();
        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();


        Map<String, Object> model = new HashMap<>();

        ListadoDeProvincias listadoDeProvinciasPorID=null;
        //carga el selected
        if (Objects.equals(context.pathParam("idProvincia"), "0")) {

            if(Objects.equals(miembro.getIdProvincia(), "")){
                // que tenga en la base y este editando el perfil
                listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(context.pathParam("2")));
            }else{
                // estes editando el perfil y ya tengas cargado una provincia
                listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(miembro.getIdProvincia().toString()));
            }
            }else{
                // quieras cambiar de provincia
            listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(context.pathParam("idProvincia")));
        }

        Provincia provincia = listadoDeProvinciasPorID.provincias.get(0);
        model.put("provincia", provincia);

        ListadoDeMunicipios listadoDeMunicipios = null;
        //carga todos los municipios que usa
        if (!Objects.equals(listadoDeProvinciasPorID.provincias.get(0), null)) {
            listadoDeMunicipios = servicioGeoref.listadoDeMunicipiosDeProvincia(Integer.parseInt(provincia.getId().toString()));
        }

        if (!Objects.equals(context.formParam("idMunicipio"), null)) {
            miembro.setIdMunicipio(Integer.parseInt(context.formParam("idMunicipio")));
        }

        //carga el selected de municipio
        if (!Objects.equals(miembro.getIdMunicipio(), 0)) {
            ListadoDeMunicipios listadoDeMunicipiosPorID = servicioGeoref.listadoDeMunicipiosPorID(miembro.getIdMunicipio());
            model.put("municipio", listadoDeMunicipiosPorID.municipios.get(0));
        }

        if(Objects.equals(miembro.getMedioDeNotificacionPreferido().getClass().getSimpleName(), "EmailSender")){
            model.put("email", true);
        }else{
            model.put("wpp", true);
        }

        if(miembro.getNotificacionInmediata()){
            model.put("inmediata", true);
        }else{
            model.put("noInmediata", true);
        }

        switch (miembro.getTurnoNotificacion()){
            case 1:  model.put("maniana", true); break;
            case 2:  model.put("tarde", true) ; break;
            case 3:  model.put("noche", true); break;
        }

        if(miembro.getEsObservador()){
            model.put("esObservador", true);
        }else{
            model.put("noEsObservador", true);
        }

        model.put("municipios", listadoDeMunicipios.municipios);
        model.put("provincias", listadoDeProvincias.provincias);
        model.put("miembro", usuario.getRol());
        this.cargarVariablesSesion(context, model);
        context.render("/login/perfil.hbs", model);
    }

    @Override
    public void show(Context context) {
        List<Usuario> usuarios = repositorioDeUsuarios.buscarTodos();

        Map<String, Object> model = this.mappearUsuarios(usuarios);

        this.cargarVariablesSesion(context, model);

        Boolean rolActualEsAdmin = (Boolean) model.get("Administrador");
        if(rolActualEsAdmin == null){
            context.render("administracion_tipos_usuarios/admin-error.hbs", model);

        }else{
            context.render("administracion_tipos_usuarios/administrar-usuarios.hbs", model);
        }

    }
public void editarRol(Context context){
        Long usuario_id = Long.valueOf(context.pathParam("id"));
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscar(usuario_id);

        Map<String, Object> model = new HashMap<>();

    List<String> rolesUsuarios = new ArrayList<>();

    rolesUsuarios.add((TipoRol.Administrador.name()));
    rolesUsuarios.add((TipoRol.Lector.name()));
    rolesUsuarios.add((TipoRol.Prestador.name()));
    rolesUsuarios.add((TipoRol.Miembro.name()));

        String email = usuario.getEmail();
        String rolUsuario = usuario.getRol().getClass().getSimpleName();
        List<String> roles = rolesUsuarios.stream().filter((rol)->!rol.equals(rolUsuario)).toList();


        model.put("usuario_email",email);
        model.put("id",usuario_id);
        model.put("usuario_rol",rolUsuario);
        model.put("roles",roles);

    this.cargarVariablesSesion(context, model);

    Boolean rolActualEsAdmin = (Boolean) model.get("Administrador");
    if(rolActualEsAdmin == null){
        context.render("administracion_tipos_usuarios/admin-error.hbs", model);

    }else {
        context.render("administracion_tipos_usuarios/editar-rol.hbs", model);
    }
}
public void guardarRol(Context context){

        String nuevoRol = context.formParam("selectRol");
        Long usuario_id = Long.valueOf(context.pathParam("id"));
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscar(usuario_id);

    if(nuevoRol != null){
        RolesUsuario rol = asignarNuevoRol(nuevoRol, usuario);
        usuario.setRol(rol);
    } else
        usuario.setRol(new Lector());

        repositorioDeUsuarios.guardar(usuario);

        this.show(context);
}

public RolesUsuario asignarNuevoRol(String nuevoRol, Usuario usuario){
        switch (nuevoRol){
            case "Administrador":
                return new Administrador();
            case "Prestador":
                return new Prestador();
            case "Miembro":
                String usuario_email = usuario.getEmail();
                MedioDeNotificacion medio = new EmailSender(new NotificarPorEmail());
                Miembro miembro = new Miembro();
                miembro.setNombre(usuario_email);
                miembro.setEmail(usuario_email);
                miembro.setMedioDeNotificacionPreferido(medio);
                miembro.setEsObservador(Boolean.FALSE);
                miembro.setIdMunicipio(0);
                miembro.setConfiabilidad(4.5);
                return miembro;
            default:
                return new Lector();
        }
}
    public void buscarUsuario(Context context){
        String usuario = context.formParam("usuario");

        List<Usuario> usuarios = repositorioDeUsuarios.buscarTodos();

        List<Usuario> usuariosFiltrados = usuarios.stream().filter(usuario1 -> usuario1.getEmail().contains(usuario)).toList();;
        Map<String, Object> model = this.mappearUsuarios(usuariosFiltrados);


        context.render("administracion_tipos_usuarios/administrar-usuarios.hbs", model);
    }
    public Map<String, Object> mappearUsuarios(List<Usuario> usuarios){

        List< Map<String, Object>> usuariosMappeados = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();

        List<String> rolesUsuarios = new ArrayList<>();

        rolesUsuarios.add((TipoRol.Administrador.name()));
        rolesUsuarios.add((TipoRol.Lector.name()));
        rolesUsuarios.add((TipoRol.Prestador.name()));
        rolesUsuarios.add((TipoRol.Miembro.name()));

        for(Usuario usuario: usuarios){
            String email = usuario.getEmail();
            String rolUsuario = usuario.getRol().getClass().getSimpleName();
            Long usuario_id = usuario.getId();
            List<String> roles = rolesUsuarios.stream().filter((rol)->!rol.equals(rolUsuario)).toList();

            Map<String,Object> usuarioMappeado= new HashMap<>();
            usuarioMappeado.put("email",email);
            usuarioMappeado.put("id",usuario_id);
            usuarioMappeado.put("rol",rolUsuario);
            usuarioMappeado.put("roles",roles);
            usuariosMappeados.add(usuarioMappeado);
        }

        model.put("usuarios",usuariosMappeados);
        return model;
    }


    @Override
    public void create(Context context) {

    }

    @Override
    public void save(Context context) throws IOException {
        Usuario usuario = (Usuario) repositorioDeUsuarios.buscar(Long.parseLong(context.sessionAttribute("usuario_id")));
        Miembro miembro = (Miembro) usuario.getRol();

        miembro.setApellido(context.formParam("apellido"));
        miembro.setNombre(context.formParam("nombre"));
        miembro.setCelular(context.formParam("celular"));
        miembro.setEsObservador(Boolean.parseBoolean(context.formParam("esObservador")));
        miembro.setIdProvincia(Long.parseLong(context.formParam("idProvincia")));
        miembro.setIdMunicipio(Integer.parseInt(context.formParam("idMunicipio")));

        MedioDeNotificacion medio=null;

        if(Objects.equals(context.formParam("medio"),"email")){
             medio = new EmailSender(new NotificarPorEmail());
        }else{
             medio = new WhatsappSender(new Twilio());
        }

        miembro.setMedioDeNotificacionPreferido(medio);
        miembro.setNotificacionInmediata(Boolean.parseBoolean(context.formParam("tipo")));
        miembro.setTurnoNotificacion(Integer.parseInt(context.formParam("turno")));

        repositorioDeRoles.actualizar(miembro);

        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();
        ListadoDeProvincias listadoDeProvinciasPorID = servicioGeoref.listadoDeProvinciasPorID(Integer.parseInt(miembro.getIdProvincia().toString()));
        Provincia provincia = listadoDeProvinciasPorID.provincias.get(0);
        ListadoDeMunicipios listadoDeMunicipios = servicioGeoref.listadoDeMunicipiosDeProvincia(Integer.parseInt(provincia.getId().toString()));
        ListadoDeMunicipios listadoDeMunicipiosPorID = servicioGeoref.listadoDeMunicipiosPorID(miembro.getIdMunicipio());
        Municipio municipio = listadoDeMunicipiosPorID.municipios.get(0);
        Map<String, Object> model = new HashMap<>();

        if(Objects.equals(miembro.getMedioDeNotificacionPreferido().getClass().getSimpleName(), "EmailSender")){
            model.put("email", true);
        }else{
            model.put("wpp", true);
        }

        if(miembro.getNotificacionInmediata()){
            model.put("inmediata", true);
        }else{
            model.put("noInmediata", true);
        }

        if(miembro.getEsObservador()){
            model.put("esObservador", true);
        }else{
            model.put("noEsObservador", true);
        }

        switch (miembro.getTurnoNotificacion()){
            case 1:  model.put("maniana", true); break;
            case 2:  model.put("tarde", true); break;
            case 3:  model.put("noche", true); break;
        }

        model.put("provincia", provincia);
        model.put("provincias", listadoDeProvincias);
        model.put("municipios", listadoDeMunicipios);
        model.put("municipio", municipio);
        model.put("miembro", miembro);
        model.put("actualizado", "Actualizado correctamente");
        this.cargarVariablesSesion(context,model);
        context.render("/login/perfil.hbs", model);
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
