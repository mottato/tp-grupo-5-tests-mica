package ar.edu.utn.frba.dds.models.incidentes;
import ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones.Turno;
import ar.edu.utn.frba.dds.models.incidentes.TurnoDeNotificaciones.TurnoNotificacion;

import java.util.Timer;
import java.util.TimerTask;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.EmailException;

import static ar.edu.utn.frba.dds.models.incidentes.Notificador.turno;

@Getter
@Setter
//Creando una tarea programada que se ejecuta cada 10 minutos
public class CronNotificador {

    private Notificador notificador;

    public CronNotificador(Notificador notificador) {
        this.notificador=notificador;
    }

    public void ejecutar(){
    Timer timer;
    timer = new Timer();

    TimerTask task = new TimerTask() {
        @Override
        public void run()
        {

            try {
                notificador.notificarPendientes(turno);
                System.out.println("Se han notificado los pendientes.");
            } catch (EmailException e) {
                throw new RuntimeException(e);
            }

        }
    };

    timer.schedule(task, 10, 600000);
    //10 minutos
    }

//10 minutos
}
