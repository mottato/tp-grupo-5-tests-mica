package ar.edu.utn.frba.dds.models.comunidades;

import ar.edu.utn.frba.dds.models.archivoCSV.AdapterCSVFileReader;
import ar.edu.utn.frba.dds.models.archivoCSV.LectorCSV;
import ar.edu.utn.frba.dds.models.georef.entities.Municipio;
import ar.edu.utn.frba.dds.models.georef.entities.Ubicacion;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioEmpresas;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioServicios;
import ar.edu.utn.frba.dds.models.servicios.Servicio;

import java.io.IOException;
import ar.edu.utn.frba.dds.models.entidades.Estacion;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVRecord;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
public class Administrador extends RolesUsuario {

  public void altaServicio(Estacion estacion, Servicio servicio) throws Exception {
    estacion.agregarServicio(servicio);
  }

  public void bajaServicio(Estacion estacion, Servicio servicio) throws Exception {
    estacion.eliminarServicio(servicio);
  }

  public void editarUbicacionServicio(Servicio servicio, Ubicacion ubicacion) {
    servicio.setUbicacion(ubicacion);
  }

  public void editarNombreServicio(Servicio servicio, String nuevoNombre) {
    servicio.setNombre(nuevoNombre);
  }

  public void editarTramosServicio(Servicio servicio, List<Municipio> nuevosTramos) {
    servicio.setTramos(nuevosTramos);
  }

  public void editarElevacionServicio(Servicio servicio, Boolean nuevosEsDeElevacion) {
    servicio.setEsDeElevacion(nuevosEsDeElevacion);
  }


  public void cargarArchivo(String archivo) throws IOException {
   /* AdapterCSVFileReader adaptadorCSV = new LectorCSV();
    try{
     List<CSVRecord> lecturaCSV =  adaptadorCSV.leerArchivoCSV(archivo);
     List<Servicio> nuevosServicios = RepositorioServicios.crearServicio(lecturaCSV);
     RepositorioEmpresas.crearEmpresa(lecturaCSV,nuevosServicios);

    } catch(IOException e){
      throw new IOException("No se ha podido leer el archivo");
    } */
  }
}
