package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.repositorios.*;

public class FactoryController {

    public static Object controller(String nombre) {
        Object controller = null;
        switch (nombre) {
            case "Login": controller = new LoginController(new RepositorioDeUsuarios(), new RepositorioDeEntidades()); break;
            case "Incidentes": controller = new IncidenteController(new RepositorioDeIncidentes()); break;
            case "Usuarios" : controller = new UsuarioController(new RepositorioDeUsuarios()); break;
            case "Archivos" : controller = new ArchivoController() ; break;
            case "Entidades" : controller = new EntidadesController(new RepositorioDeEntidades()) ; break;
            case "Establecimientos" : controller = new EstablecimientosController(new RepositorioDeEstablecimientos(), new RepositorioDeEntidades()) ; break;
            case "Servicios" : controller = new ServiciosController(new RepositorioDeServicios()) ; break;
            case "Comunidades": controller = new ComunidadesController(new RepositorioDeComunidades()); break;
            case "Informes" : controller = new InformeController();

        }
        return controller;
    }
}
