package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import ar.edu.utn.frba.dds.models.helpers.ValidadorContrasenia;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeUsuarios;
import ar.edu.utn.frba.dds.server.utils.ICrudViewsHandler;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginController extends Controller implements ICrudViewsHandler {

    private RepositorioDeUsuarios repositorioDeUsuarios;
    private RepositorioDeEntidades repositorioDeEntidades;

    public LoginController(RepositorioDeUsuarios repositorioDeUsuarios, RepositorioDeEntidades repositorioDeEntidades){
        this.repositorioDeUsuarios = repositorioDeUsuarios;
        this.repositorioDeEntidades = repositorioDeEntidades;
    }
    @Override
    public void index(Context context) {
        context.render("login/login.hbs");
    }

    public void register(Context context) {
        context.render("login/registro.hbs");
    }

    @Override
    public void show(Context context) {
    }

    @Override
    public void create(Context context){}

    public void login(Context context){

        List<Usuario> usuarios = (List<Usuario>) repositorioDeUsuarios.login(context.formParam("email"), context.formParam("password"));
        Map<String, Object> model = new HashMap<>();

        if(!usuarios.isEmpty())
        {
            Usuario usuario = usuarios.get(0);
            this.cargarSesion(context, usuario);
            context.redirect("bienvenida");
        }
        else
        {
            System.out.println("entre al else");
            String error = "No se encontró un usuario con ese password.";
            model.put("error", error);
            context.render("login/login.hbs", model);
        }
    }

    public void cargarSesion(Context context, Usuario usuario){
        context.sessionAttribute("usuario_id", usuario.getId().toString());
        context.sessionAttribute("email", usuario.getEmail());
        context.sessionAttribute("tipo_rol", usuario.getRol().getClass().getSimpleName());


        switch (context.sessionAttribute("tipo_rol").toString()){
            case "Administrador": context.sessionAttribute("Administrador", true); break;
            case "Prestador": context.sessionAttribute("Prestador", true); break;
            case "Miembro": context.sessionAttribute("Miembro", true);

                Miembro miembro = (Miembro) usuario.getRol();
                System.out.println(miembro.getComunidades().get(0).getNombre());

                if(miembro.esAdmin())
                {
                    context.sessionAttribute("MiembroAdmin", true);

                }
                break;
            case "Lector": context.sessionAttribute("Lector", true); break;
        }
    }

    public void limpiarSesion(Context context){

        context.sessionAttribute("usuario_id", null);
        context.sessionAttribute("email", null);
        context.sessionAttribute("tipo_rol", null);
        context.sessionAttribute("Administrador", null);
        context.sessionAttribute("Prestador", null);
        context.sessionAttribute("Miembro", null);
        context.sessionAttribute("MiembroAdmin", null);
        context.sessionAttribute("Lector", null);
    }

    public void registrar(Context context) throws Exception {

        //esto hay que hacerlo mejor
        ValidadorContrasenia validador = new ValidadorContrasenia();

        // ver bloque try catch
        // no tiene en cuenta pass trivial
        if(validador.laContraseniaEsValida(context.formParam("password"))){
            //ver bloque try
            System.out.println("creando usuario");
            Usuario nuevoUsuario = new Usuario(context.formParam("email"),context.formParam("password"));
            //x defecto los usuarios se crean como lectores...
            this.repositorioDeUsuarios.guardar(nuevoUsuario);

            Map<String, Object> model = new HashMap<>();
            model.put("usuario", nuevoUsuario);

            context.sessionAttribute("usuario_id", nuevoUsuario.getId().toString());
            context.sessionAttribute("email", nuevoUsuario.getEmail());
            context.sessionAttribute("tipo_rol", nuevoUsuario.getRol().getClass().getSimpleName());
            context.sessionAttribute("Lector", true);

            //x ahora redirecciona aca
            context.redirect("bienvenida");

        }else{

            System.out.println("validacion de usuario no funciona");
            Map<String, Object> model = new HashMap<>();
            String error = "El password no cumple con los requisitos.";
            model.put("error", error);
            context.render("login/registro.hbs", model);

        }

    }

    public void bienvenida(Context context){
        Map<String, Object> model = new HashMap<>();
        this.cargarVariablesSesion(context, model);
        context.render("login/bienvenida.hbs", model);

    }

    public void logout(Context context){
        /* limpiar las variables de sesion */
        this.limpiarSesion(context);
        this.index(context);
    }

    @Override
    public void save(Context context) {
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


}
