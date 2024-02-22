package ar.edu.utn.frba.dds.models.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "centroide")
public class Centroide {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public int id;
    @Column(name = "lat")
    public float lat;
    @Column(name = "lon")
    public float lon;

}
