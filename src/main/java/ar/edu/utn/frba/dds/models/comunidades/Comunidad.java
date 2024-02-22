package ar.edu.utn.frba.dds.models.comunidades;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ar.edu.utn.frba.dds.models.Persistente;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Comunidad")
public class Comunidad extends Persistente {

  @Column(name = "nombre")
  private String nombre;
  /*@ManyToMany
  @JoinTable(
          name = "comunidad_miembros",
          joinColumns = @JoinColumn(name = "comunidad_id"),
          inverseJoinColumns = @JoinColumn(name = "miembro_id")
  )*/
  @ManyToMany
  @JoinTable(
          name = "miembros_comunidad",
          joinColumns = @JoinColumn(name = "comunidad_id"),
          inverseJoinColumns = @JoinColumn(name = "miembro_id")
  )
  private List<Miembro> miembros;
  @ManyToMany
  @JoinTable(
          name = "comunidad_administradores",
          joinColumns = @JoinColumn(name = "comunidad_id"),
          inverseJoinColumns = @JoinColumn(name = "miembro_id")
  )
  private List<Miembro> administradores;

  @ManyToMany
  @JoinTable(
          name = "solicitud_comunidad",
          joinColumns = @JoinColumn(name = "comunidad_id"),
          inverseJoinColumns = @JoinColumn(name = "usuario_id")
  )
  private List<Usuario> solicitudes;

  @Column(name = "confiabilidad")
  private Double confiabilidad;


  public Comunidad(){
    this.miembros = new ArrayList<>();
    this.administradores = new ArrayList<>();
    this.solicitudes= new ArrayList<>();
  }

  public Comunidad(String nombre){
    this.nombre = nombre ;
    this.miembros = new ArrayList<>();
    this.administradores = new ArrayList<>();
    this.solicitudes= new ArrayList<>();
  }


  public void agregarMiembro(Miembro miembro) {
    this.miembros.add(miembro);
  }

  public void eliminarMiembro(Miembro miembro) {
    this.miembros.remove(miembro);
  }

  public void sacarAdministrador(Miembro miembro){
      this.administradores.remove(miembro);
    }

  public void darAdministradorA(Miembro miembro) {

    this.administradores.add(miembro);
  }

  public int cantMiembros() {
    return this.miembros.size();
  }

  public void agregarSolicitud(Usuario usuario){
    this.solicitudes.add(usuario);
  }

  public void eliminarSolicitud(Usuario usuario){
    this.solicitudes.remove(usuario);
  }

  public boolean esMiembro(Miembro miembro) {
    return getMiembros().contains(miembro);
  }

  public boolean envioSolicitud(Usuario usuario){
    return  getSolicitudes().contains(usuario);
  }

  public boolean esAdmin(Miembro miembro){
    return getAdministradores().contains(miembro);
  }

}

