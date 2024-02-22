package ar.edu.utn.frba.dds.models.entidades;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Linea extends Entidad {

  private MedioDeTransporte medioDeTransporte;
  private List<Estacion> estaciones;

  public Linea(String leyenda, MedioDeTransporte medioDeTransporte) {
    super.leyenda = leyenda;
    this.medioDeTransporte = medioDeTransporte;
    this.estaciones = new ArrayList<>();
  }

  public void agregarEstacion(Estacion estacion){
    estaciones.add(estacion);
  }

  public void eliminarEstacion(Estacion estacion){
    estaciones.remove(estacion);
  }

}
