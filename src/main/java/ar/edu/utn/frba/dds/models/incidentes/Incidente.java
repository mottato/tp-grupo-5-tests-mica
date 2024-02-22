package ar.edu.utn.frba.dds.models.incidentes;

import ar.edu.utn.frba.dds.models.Persistente;
import ar.edu.utn.frba.dds.models.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.comunidades.RolesUsuario;
import ar.edu.utn.frba.dds.models.converters.LocalDateTimeAttributeConverter;
import ar.edu.utn.frba.dds.models.incidentes.mensajes.AperturaIncidente;
import ar.edu.utn.frba.dds.models.incidentes.mensajes.CierreIncidente;
import ar.edu.utn.frba.dds.models.incidentes.mensajes.Mensaje;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.EmailException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "incidente")
public class Incidente extends Persistente {


    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    private Servicio servicio;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "abierto_por", referencedColumnName = "id")
    private Miembro abiertoPor;

    @Column(name = "estado")
    private Boolean estado;

    @Column(name = "observacionesApertura")
    private String observacionesApertura;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "fechaApertura")
    private LocalDateTime fechaApertura;


    public Incidente(Servicio servicio, Miembro abiertoPor, String observaciones) throws EmailException {
        this.servicio = servicio;
        this.abiertoPor = abiertoPor;
        this.observacionesApertura = observaciones;
        this.fechaApertura = LocalDateTime.now();
        this.estado=Boolean.TRUE;
    }

    public Incidente(){

    }

    public void notificarCercania(String mensaje,List<Comunidad> comunidades){}

    public void cerrar() throws EmailException {

        this.estado = Boolean.FALSE;

    }

}
