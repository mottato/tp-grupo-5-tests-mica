package ar.edu.utn.frba.dds.server.handlers;

import ar.edu.utn.frba.dds.server.exceptions.AccessDeniedException;
import io.javalin.Javalin;

public class AccessDeniedHandler implements IHandler {

    @Override
    public void setHandle(Javalin app) {
        app.exception(AccessDeniedException.class, (e, context) -> {
            context.render("401.hbs");
        });
    }
}