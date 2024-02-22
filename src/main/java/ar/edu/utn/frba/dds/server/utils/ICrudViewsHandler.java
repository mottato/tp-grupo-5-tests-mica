package ar.edu.utn.frba.dds.server.utils;

import io.javalin.http.Context;

import java.io.IOException;

public interface ICrudViewsHandler {
    void index(Context context);
    void show(Context context);
    void create(Context context) throws Exception;
    void save(Context context) throws IOException;
    void edit(Context context) throws IOException;
    void update(Context context);
    void delete(Context context);
}
