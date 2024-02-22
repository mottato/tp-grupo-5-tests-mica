package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.models.incidentes.CronNotificador;
import ar.edu.utn.frba.dds.models.incidentes.Notificador;
import ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones.Turno;

public class Notificar {

    public static Notificador notificador;

    public static void init() {

        Turno turno = new Turno();
        notificador = new Notificador(turno);
        CronNotificador cronNotificador = new CronNotificador(notificador);
        cronNotificador.ejecutar();
        System.out.println("Ejecutando tarea programada");

    }

}
