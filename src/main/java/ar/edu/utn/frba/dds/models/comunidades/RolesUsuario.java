package ar.edu.utn.frba.dds.models.comunidades;

import ar.edu.utn.frba.dds.models.Persistente;
import ar.edu.utn.frba.dds.models.helpers.Permiso;
import io.javalin.security.RouteRole;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RolesUsuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class RolesUsuario extends Persistente implements RouteRole {


  @ManyToMany
  private Set<Permiso> permisos;
  public void desactivarAlertas() {
  }

  public RolesUsuario() {
    this.permisos = new HashSet<>();
  }

  public void agregarPermisos(Permiso ... permisos) {
    Collections.addAll(this.permisos, permisos);
  }

  public boolean tenesPermiso(Permiso permiso) {
    return this.permisos.contains(permiso);
  }

  public boolean tenesPermiso(String nombreInterno) {
    return this.permisos.stream().anyMatch(p -> p.coincideConNombreInterno(nombreInterno));
  }

}
