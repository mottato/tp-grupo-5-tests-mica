package ar.edu.utn.frba.dds.models.comunidades;
import ar.edu.utn.frba.dds.models.converters.MedioDeNotificacionAttributeConverter;
import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;
import ar.edu.utn.frba.dds.models.incidentes.TipoNotificacion;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.MedioDeNotificacion;
import ar.edu.utn.frba.dds.models.servicios.Servicio;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.EmailException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Entity
public class Miembro extends RolesUsuario {

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "celular")
    private String celular;

    @Convert(converter = MedioDeNotificacionAttributeConverter.class)
    @Column(name = "medioNotificacionPreferido")
    private MedioDeNotificacion medioDeNotificacionPreferido;

    @Column(name="notificacionInmediataa")
    private Boolean notificacionInmediata;

    @Column(name="turno")
    private Integer turnoNotificacion;

//    private TipoNotificacion tipoNotificacion;

    @ManyToMany()
    @JoinTable(
            name = "miembros_comunidad",
            joinColumns = @JoinColumn(name = "miembro_id"),
            inverseJoinColumns = @JoinColumn(name = "comunidad_id")
    )
    private List<Comunidad> comunidades;

    @OneToMany(mappedBy = "miembro")
    private List<ServicioDeInteres> serviciosDeInteres;


    @Column(name = "esObservador")
    private Boolean esObservador;

    //revisar porque el email ya esta en usuario
    @Column(name = "email")
    private String email;

    @Column(name = "confiabilidad")
    private Double confiabilidad;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "provincia_id")
    private Long idProvincia;

    @Column(name = "municipio_id")
    private int idMunicipio;

    public Miembro(){

    }

    public Miembro(String nombre, String apellido){
        this.nombre=nombre;
        this.apellido=apellido;
        this.comunidades= new ArrayList<>();
    }

    public void miembroAceptado(Comunidad comunidad){
        this.comunidades.add(comunidad);
    }


    public Incidente abrirIncidente(Servicio servicio, String observaciones){
        Incidente incidente=null;
        Miembro yo = this;
            try {
                incidente=new Incidente(servicio,yo,observaciones);
            } catch (EmailException e) {
                throw new RuntimeException(e);
            }
        ;
        return incidente;
    }

    public List<IncidenteXComunidad> incidentesXComunidad(Incidente incidente, List<Comunidad> comunidades){
        List<IncidenteXComunidad> incidentesXComunidad = new ArrayList<>();
        Miembro yo = this;

        comunidades.forEach(comunidad -> {
            try {
                incidentesXComunidad.add(new IncidenteXComunidad(comunidad, incidente));
            } catch (EmailException e) {
                System.out.println("Fallo al crear incidente x comunidad");
                throw new RuntimeException(e);
            }
        });
        return incidentesXComunidad;
    }

    public void sosParte(Comunidad comunidad){
        comunidades.add(comunidad);
    }

    public boolean esAdmin(){
        Miembro miembro = this;
        Boolean es;
        for (Comunidad comunidad : comunidades) {
            if(comunidad.getAdministradores().contains(miembro)){
                return true;
            }
        };

        return false;
    }

    public  boolean esAdminDe(Comunidad comunidad){
        return comunidad.getAdministradores().contains(this);
    }

    public List<Comunidad> comunidadesAdministradas(){
         return comunidades.stream()
                .filter(comunidad -> comunidad.getAdministradores().contains(this))
                .collect(Collectors.toList());
    }



}
