package ar.edu.utn.frba.dds.models.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class Provincia {
    public Long id;
    public String nombre;
    public Centroide centroide;

    public Provincia(){}

}
