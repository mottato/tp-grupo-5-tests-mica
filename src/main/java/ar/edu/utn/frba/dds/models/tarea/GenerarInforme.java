package ar.edu.utn.frba.dds.models.tarea;

public class GenerarInforme implements Tarea{
  @Override
  public void ejecutar() {
    System.out.println("Generando informe");
  }
}
