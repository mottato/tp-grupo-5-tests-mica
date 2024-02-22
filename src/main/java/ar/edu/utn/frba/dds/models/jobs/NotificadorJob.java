package ar.edu.utn.frba.dds.models.jobs;

import ar.edu.utn.frba.dds.models.tarea.Tarea;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
@Setter
public class NotificadorJob {
  private long periodoDeEjecucion=0;
  private List<Tarea> tareasAEjecutar = new ArrayList<Tarea>();
  private Timer time = new Timer();

  public NotificadorJob(long periodoDeEjecucion) {
    this.periodoDeEjecucion = periodoDeEjecucion;
  }

  public void agregarTarea(Tarea tarea){
    this.tareasAEjecutar.add(tarea);
  }

  public void ejecutarJob(){
    JobTask task = new JobTask(tareasAEjecutar);
    time.schedule(task ,new Date(),this.periodoDeEjecucion);
  }
  public void finalizarJob(){
    this.time.cancel();
  }
}
