package ar.edu.utn.frba.dds.models.comunidades;

import io.javalin.security.RouteRole;

public enum TipoRol implements RouteRole {
    Administrador,
    Lector,
    Prestador,

    Miembro

}