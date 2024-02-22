package ar.edu.utn.frba.dds.models.helpers;

import java.net.MalformedURLException;

public interface IValidable {

  void validarPassword(String nombreUsuario, String contrasenia) throws MalformedURLException;
}
