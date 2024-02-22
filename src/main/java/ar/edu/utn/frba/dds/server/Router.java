package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.*;
import ar.edu.utn.frba.dds.models.comunidades.TipoRol;

import static io.javalin.apibuilder.ApiBuilder.*;
public class Router {
    public static void init() {
        /*
        Server.app().get("/", ctx -> {
            ctx.sessionAttribute("item1", "Cosa 1");
            ctx.result("Hola mundo");
        });*/

        Server.app().get("/", ((LoginController) FactoryController.controller("Login"))::index);

        /*
        Server.app().get("/saluda", ctx -> {
            ctx.result("Hola "
                    + ctx.queryParam("nombre")
                    + ", " + ctx.sessionAttribute("item1")
            );
        });
        */
        Server.app().get("/saludo-para/{nombre}", ctx -> ctx.result("Hola "
                + ctx.pathParam("nombre")
        ));

        Server.app().routes(() -> {

            /* loginController */
            get("login", ((LoginController) FactoryController.controller("Login"))::index);
            post("login", ((LoginController) FactoryController.controller("Login"))::login);
            get("registro", ((LoginController) FactoryController.controller("Login"))::register);
            post("registro", ((LoginController) FactoryController.controller("Login"))::registrar);
            get("bienvenida", ((LoginController) FactoryController.controller("Login"))::bienvenida);
            get("logout", ((LoginController) FactoryController.controller("Login"))::logout);

            /* Archivos Controller */
            get("cargar-organizaciones", ((ArchivoController) FactoryController.controller("Archivos"))::index);
            post("cargar-organizaciones-exitosa", ((ArchivoController) FactoryController.controller("Archivos"))::upload);

            /* Administracion de Usuarios */
            get("administrar-usuarios", ((UsuarioController) FactoryController.controller("Usuarios"))::show);
            get("editar-rol/{id}", ((UsuarioController) FactoryController.controller("Usuarios"))::editarRol);
            post("editar-rol/{id}", ((UsuarioController) FactoryController.controller("Usuarios"))::guardarRol);
            post("administrar-usuarios", ((UsuarioController) FactoryController.controller("Usuarios"))::buscarUsuario);
            get("perfil/{idProvincia}/editar",  ((UsuarioController) FactoryController.controller("Usuarios"))::perfil);
            post("perfil/{idProvincia}/editar", ((UsuarioController) FactoryController.controller("Usuarios"))::save);

            /* Incidentes Controller*/

            get("incidentes", ((IncidenteController) FactoryController.controller("Incidentes"))::show);
            get("apertura-incidente", ((IncidenteController) FactoryController.controller("Incidentes"))::index);
            post("apertura-incidente", ((IncidenteController) FactoryController.controller("Incidentes"))::save);
            get("incidente/{incidente_id}/cerrar", ((IncidenteController) FactoryController.controller("Incidentes"))::cerrar);
            post("incidente/cierre-incidente", ((IncidenteController) FactoryController.controller("Incidentes"))::cerrando);

            /* Miembros Controller */

            get("comunidades", ((ComunidadesController) FactoryController.controller("Comunidades"))::index);
            get("/comunidades/{comunidad_id}/unirme/{usuario_id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::crearSolicitud);
            get("/administrar-comunidad", ((ComunidadesController) FactoryController.controller("Comunidades"))::adminComunidad);
            get("comunidades/{comunidad_id}/gestionar",((ComunidadesController) FactoryController.controller("Comunidades"))::gestionar);
            get("comunidades/{comunidad_id}/aceptar/{usuario_id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::aceptarSolicitud);
            get("comunidades/{comunidad_id}/eliminar/{miembro_id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::eliminar);
            get("comunidades/{comunidad_id}/hacer-administrador/{miembro_id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::hacerAdmin);
            get("comunidades/{comunidad_id}/quitar-administrador/{miembro_id}", ((ComunidadesController) FactoryController.controller("Comunidades"))::quitarAdmin);
            get("/comunidades/crear",  ((ComunidadesController) FactoryController.controller("Comunidades"))::create);
            post("/comunidades/editar/{comunidad_id}" , ((ComunidadesController) FactoryController.controller("Comunidades"))::save);
            get("/comunidades/editar/{comunidad_id}" , ((ComunidadesController) FactoryController.controller("Comunidades"))::edit);


            /* Entidades Controller */

            get("entidades", ((EntidadesController) FactoryController.controller("Entidades"))::index);
            get("entidades/crear", ((EntidadesController) FactoryController.controller("Entidades"))::create);
            get("entidades/{id}/editar", ((EntidadesController) FactoryController.controller("Entidades"))::edit);
            post("entidades/{id}", ((EntidadesController) FactoryController.controller("Entidades"))::update);
            post("entidades", ((EntidadesController) FactoryController.controller("Entidades"))::save);
            get("entidades/{id}/establecimientos", ((EntidadesController) FactoryController.controller("Entidades"))::showEstablecimientos);
            get("entidades/carga-masiva", ((EntidadesController) FactoryController.controller("Entidades"))::cargaMasiva);

            /* Establecimientos Controller */

            get("establecimientos/{idEntidad}/crear", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::create);
            // deprecated get("establecimientos/{idEntidad}/crear/{idProvincia}/{leyenda}", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::cargarMunicipios);
            post("establecimientos", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::save);
            get("establecimientos/{id}/{idProvincia}/editar/{leyenda}", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::edit);
            post("establecimientos/{id}", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::update);
            get("establecimientos/{id}/servicios", ((EstablecimientosController) FactoryController.controller("Establecimientos"))::showServicios);

            /* Servicios Controller */
            get("servicios/{idEstablecimiento}/crear", ((ServiciosController) FactoryController.controller("Servicios"))::create);
            post("servicios", ((ServiciosController) FactoryController.controller("Servicios"))::save);
            get("servicios/{id}/editar", ((ServiciosController) FactoryController.controller("Servicios"))::edit);
            post("servicios/{id}", ((ServiciosController) FactoryController.controller("Servicios"))::update);

            /* Informe Controller */
            get("generar-informes-incidentes",((InformeController) FactoryController.controller("Informes"))::show);
            post("generar-informes-incidentes", ((InformeController) FactoryController.controller("Informes"))::create);


        });
    }
}

