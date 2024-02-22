package ar.edu.utn.frba.dds.models.tarea;

public class NotificarSinApuro implements Tarea {
  @Override
  public void ejecutar() {
    System.out.println("Notificando sin apuro");
  }
}
