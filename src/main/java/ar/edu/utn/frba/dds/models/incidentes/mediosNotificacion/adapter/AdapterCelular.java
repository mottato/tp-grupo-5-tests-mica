package ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapter;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;

public interface AdapterCelular {

    void enviarNotificacion(Miembro miembro, String notificacion);

}
