package ar.edu.utn.frba.dds.models.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "municipio")
public class Municipio {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nombre")
    public String nombre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "centroide_id")
    public Centroide centroide;

   // public Provincia provincia;

    public Municipio() {
    }

    public Municipio(Long id, String nombre, Centroide centroide) {
        this.id = id;
        this.nombre = nombre;
        this.centroide = centroide;
    }

    public Municipio(Long id) {
        this.id = id;
    }
}
