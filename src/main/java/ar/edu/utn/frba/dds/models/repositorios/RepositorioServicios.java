package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.entidades.Establecimiento;
import ar.edu.utn.frba.dds.models.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.models.georef.entities.Ubicacion;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RepositorioServicios {
    public static List<Servicio> servicios = new ArrayList<>();

    public static void agregarServicio(Servicio servicio){
        servicios.add(servicio);
    }

    public static List<Servicio> crearServicio(List<CSVRecord> lectura) throws IOException {
        List<Servicio> nuevosServicio = new ArrayList<Servicio>();
        for(int i = 0;i<lectura.size();i++){

            int codigoServico = Integer.parseInt(lectura.get(i).get("Codigo Servicio"));
            String nombreServicio = lectura.get(i).get("Nombre Servicio");
            String municipio = lectura.get(i).get("Municipio");
            String esServicioDeElvacion = lectura.get(i).get("Es de elevacion").toLowerCase();
            String elServicioEstaActivo = lectura.get(i).get("Esta Activo");
            Boolean esDeElvacion = esServicioDeElvacion.contains("si") ? true : false;
            Boolean estaActivo = elServicioEstaActivo.contains("si");

            ListadoDeMunicipios municipios = ServicioGeoref.getInstancia().listadoDeMunicipiosPorNombre(municipio);
            /*
            Municipio ubicacionDelServicio = new Municipio();
            for(Municipio mun: municipios.municipios) {
                if(mun.nombre == municipio) {
                    ubicacionDelServicio = new Municipio(mun.id, mun.nombre, mun.centroide);
                }
            }*/
            Ubicacion ubicacionDelServicio = new Ubicacion();
            Servicio nuevoServicio = new Servicio(codigoServico,nombreServicio,ubicacionDelServicio,esDeElvacion,estaActivo, new Establecimiento());
            nuevosServicio.add(nuevoServicio);
            agregarServicio(nuevoServicio);
        }
        return nuevosServicio;

    }
}
