package ar.edu.utn.frba.dds.models.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "ubicacion")
public class Ubicacion {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id;
    @Column(name = "lat")
    public float lat;
    @Column(name = "lon")
    public float lon;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="centroide_id", nullable=false)
    private Centroide centroide;

    public Ubicacion(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Ubicacion() {
    }
}