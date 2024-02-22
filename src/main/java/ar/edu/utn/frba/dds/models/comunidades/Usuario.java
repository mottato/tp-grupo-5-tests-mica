package ar.edu.utn.frba.dds.models.comunidades;

import ar.edu.utn.frba.dds.models.Persistente;
import ar.edu.utn.frba.dds.models.georef.entities.Municipio;
import ar.edu.utn.frba.dds.models.helpers.IniciarSesion;
import ar.edu.utn.frba.dds.models.helpers.ValidadorContrasenia;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "Usuario")
public class Usuario extends Persistente {

  @Column(name = "contrasenia")
  private String contrasenia;
  @Column(name = "email")
  private String email;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "rol_id")
  private RolesUsuario rol;


  public Usuario(){

  }

  public Usuario(String email, String contrasenia)
        throws Exception
  {

    try{
      if(ValidadorContrasenia.laContraseniaEsValida(contrasenia))
      {
        this.contrasenia=contrasenia;
        this.email=email;
        this.rol=new Lector();
      }
      else{
        System.out.println("El password no cumple con los requerimientos de seguridad.");
      }
    }catch (Exception e){
      new Exception("No se ha podido registrar un nuevo usuario.");
    }

  }

  private void iniciarSesion(String nombreUsuario, String contrasenia) {
    IniciarSesion inicioDeSesion = new IniciarSesion(this);
    inicioDeSesion.validarUsuario(nombreUsuario, contrasenia);
  }

 /* public void solicitarSerMiembro(Comunidad comunidad){
    comunidad.agregarMiembro(this);
    comunidades.add(comunidad);
  }*/

  public boolean esAdministrador(Comunidad comunidad){
    return comunidad.getAdministradores().contains(this);
  }

}
