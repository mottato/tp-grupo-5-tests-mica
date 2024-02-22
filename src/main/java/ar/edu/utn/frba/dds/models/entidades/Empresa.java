package ar.edu.utn.frba.dds.models.entidades;

import ar.edu.utn.frba.dds.models.servicios.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "codigoEmpresa")
    private int codigoEmpresa;
    @Column(name = "nombreEmpresa")
    private String nombreEmpresa;
    @Column(name = "usuarioResponsable")
    private String usuarioResponsable;
    @OneToMany(mappedBy = "empresa")
    private List<Servicio> serviciosQuePresta = new ArrayList<Servicio>();

    public Empresa(){

    }

    public Empresa(int codigoEmpresa, String nombreEmpresa, String usuarioResponsable) {
        this.codigoEmpresa = codigoEmpresa;
        this.nombreEmpresa = nombreEmpresa;
        this.usuarioResponsable = usuarioResponsable;
    }
    public void darServicioDeAlta(Servicio nuevoServicio){
        this.serviciosQuePresta.add(nuevoServicio);
    }
}
