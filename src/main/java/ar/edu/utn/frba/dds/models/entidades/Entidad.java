package ar.edu.utn.frba.dds.models.entidades;

import ar.edu.utn.frba.dds.models.Persistente;
import ar.edu.utn.frba.dds.models.comunidades.Prestador;
import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Entidad")
public class Entidad extends Persistente {

    private Long id;
    @Column(name = "leyenda")
    public String leyenda;
    @OneToMany(mappedBy = "entidad")
    public List<Establecimiento> establecimientos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "prestador_id", referencedColumnName = "id")
    private Prestador prestador;

    public Entidad(){

    }

    public List<Incidente> listarIncidentes(LocalDateTime desde, LocalDateTime hasta) {
        //query de incidentes de esa entidad
        return null;
    }

    public int cantEstablecimientos() {
        return this.establecimientos.size();
    }

    public List<Establecimiento> misEstablecimientos() {return this.establecimientos; }

}
