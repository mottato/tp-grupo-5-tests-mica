package ar.edu.utn.frba.dds.models.helpers;

import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import lombok.Getter;
import lombok.Setter;

public class IniciarSesion {
  private final Usuario usuario;
  @Getter
  @Setter
  private static int cantidadIntentosDeLogin = 3;

  public IniciarSesion(Usuario usuario) {
    this.usuario = usuario;
  }

  public boolean validarUsuario(String email, String contrasenia) {
    if (cantidadIntentosDeLogin != 0) {
      if (usuario.getEmail().equals(email)
              && usuario.getContrasenia().equals(contrasenia)) {
        System.out.println("Inicio de sesión exitoso");
        return true;
      } else {
        cantidadIntentosDeLogin--;
        System.out.println("Usuario o contraseña incorrecta. Intente nuevamente. "
                + "Intentos restantes: "+ cantidadIntentosDeLogin + "\n");
        return false;
      }
    } else {
      System.out.print("Ha superado la cantidad máxima de intentos de inicio de sesión."
              + " Intente más tarde nuevamente");
      return false;
    }
  }


}
