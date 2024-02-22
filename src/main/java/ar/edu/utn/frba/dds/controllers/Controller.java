package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public abstract class Controller implements WithSimplePersistenceUnit {

    protected Usuario usuarioLogueado(Context ctx) {
        if(ctx.sessionAttribute("id") == null)
            return null;
        return entityManager()
                .find(Usuario.class, Long.parseLong(ctx.sessionAttribute("id")));
    }

    protected void cargarVariablesSesion(Context context, Map<String, Object> model){

        model.put("email", context.sessionAttribute("email"));
        model.put("tipo_rol", context.sessionAttribute("tipo_rol"));
        model.put("usuario_id",context.sessionAttribute("usuario_id"));
        model.put("MiembroAdmin", context.sessionAttribute("MiembroAdmin"));
        model.put("Miembro", context.sessionAttribute("Miembro"));
        model.put("Administrador", context.sessionAttribute("Administrador"));
        model.put("Prestador", context.sessionAttribute("Prestador"));
        model.put("Lector", context.sessionAttribute("Lector"));
    }

}
