package ar.edu.utn.frba.dds.models.incidentes;

import ar.edu.utn.frba.dds.models.Persistente;
import ar.edu.utn.frba.dds.models.comunidades.Comunidad;
import ar.edu.utn.frba.dds.models.comunidades.Miembro;
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

import static ar.edu.utn.frba.dds.models.incidentes.Notificador.turno;
import static ar.edu.utn.frba.dds.server.Notificar.notificador;

@Getter
@Setter
@Entity
@Table(name = "incidente_comunidad")
public class IncidenteXComunidad extends Persistente {

    @ManyToOne
    @JoinColumn(name = "incidente_id", referencedColumnName = "id")
    private Incidente incidente;

    @ManyToOne
    @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
    private Comunidad comunidad;

    @Column(name = "observacionesCierre")
    private String observacionesCierre;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cerrado_por", referencedColumnName = "id")
    private Miembro cerradoPor;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    @Column(name = "fechaCierre")
    private LocalDateTime fechaCierre;


    public IncidenteXComunidad(Comunidad comunidad, Incidente incidente) throws EmailException {

        this.comunidad = comunidad;
        this.incidente = incidente;

        //llamar a notificador pasarle la comunidad y el incidente

        Mensaje mensaje = new AperturaIncidente();

        notificador.notificar(comunidad, this, mensaje, turno);

    }

    public void cerrar(String observacionesCierre, Miembro miembro) throws EmailException {

        this.observacionesCierre=observacionesCierre;
        this.cerradoPor = miembro;
        this.fechaCierre = LocalDateTime.now();

        Mensaje mensaje = new CierreIncidente();

        notificador.notificar(comunidad, this, mensaje, turno);

    }

    public IncidenteXComunidad(){

    }



}
