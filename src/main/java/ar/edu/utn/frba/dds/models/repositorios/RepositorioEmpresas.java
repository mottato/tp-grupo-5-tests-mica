package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.entidades.Empresa;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class RepositorioEmpresas{
        public static List<Empresa> empresas = new ArrayList<Empresa>();

        public static void crearEmpresa (List<String> lecturaCsv, List<Servicio> nuevosServicios){
            for(int i = 0;i < lecturaCsv.size();i++){
                String [] val = lecturaCsv.get(i).split(";");
                Empresa nuevaEmpresa = new Empresa(Integer.parseInt(val[0]),val[1],val[2]);
                System.out.println(nuevaEmpresa);


            }
        }


}
