package ar.edu.utn.frba.dds.models.helpers;

import ar.edu.utn.frba.dds.models.entidades.Empresa;
import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.entidades.Establecimiento;
import ar.edu.utn.frba.dds.models.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.georef.entities.*;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEmpresas;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEntidades;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeEstablecimientos;
import ar.edu.utn.frba.dds.models.repositorios.RepositorioDeServicios;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import java.io.IOException;
import java.util.List;

public class CargadorDeEntidades {

    private static RepositorioDeEmpresas repositorioDeEmpresas = new RepositorioDeEmpresas();
    private static RepositorioDeServicios repositorioDeServicios = new RepositorioDeServicios();
    private static RepositorioDeEntidades repositorioDeEntidades = new RepositorioDeEntidades();
    private static RepositorioDeEstablecimientos repositorioDeEstablecimientos =new RepositorioDeEstablecimientos();
    private static ServicioGeoref georef = ServicioGeoref.getInstancia();

    public static void cargarEntidades( List<String> lecturaCsv) throws IOException {
        Object factory = null;
        for(int i = 0;i < lecturaCsv.size();i++){
            String [] val = lecturaCsv.get(i).split(";");

            // Empresa
            Integer codigoEmpresa = Integer.parseInt(val[0]);
            String nombreEmpresa = val[1];
            String usuarioResponsable = val[2];

            // Servicio
            int codigoServicio = Integer.parseInt(val[3]);
            String nombreServicio = val[4];
            String provincia = val[5];
            String municipio = val[6];
            ListadoDeMunicipios municipios = georef.listadoDeMunicipiosPorNombre(municipio);
            Municipio municipioServicio = municipios.municipios.get(0);
            ListadoDeProvincias provincias = georef.listadoDeProvincias();
            Provincia provinciaServicio = provincias.buscarProvinciaPorNombre(provincia);
            Boolean esDeElvacion = BooleanConverter.StringToBooleanConverter((val[7]));
            Boolean estaActivo = BooleanConverter.StringToBooleanConverter((val[8]));

            // Entidad
            String leyendaEntidad = val[10];
            Entidad entidad = new Entidad();
            entidad.setLeyenda(leyendaEntidad);

            // Establecimiento
            Establecimiento establecimiento = new Establecimiento();
            String leyendaEstablecimiento = val[9];
            establecimiento.setLeyenda(leyendaEstablecimiento);
            establecimiento.setIdMunicipio(Math.toIntExact(municipioServicio.id));
            establecimiento.setIdProvincia(provinciaServicio.id);

            Servicio nuevoServicio = new Servicio(codigoServicio,nombreServicio,esDeElvacion,estaActivo,establecimiento);

            List<Object> empresaXCodigo = repositorioDeEmpresas.buscarEmpresaXCodigo(codigoEmpresa);

            List<Object> entidadXCodigo = repositorioDeEntidades.buscarEntidadXLeyenda(leyendaEntidad);

            if(entidadXCodigo.isEmpty()){
                repositorioDeEntidades.guardar(entidad);
                establecimiento.setEntidad(entidad);
            } else{
                establecimiento.setEntidad((Entidad) entidadXCodigo.get(0));
            }
            repositorioDeEstablecimientos.guardar(establecimiento);

            if(empresaXCodigo.isEmpty()){
                Empresa nuevaEmpresa = new Empresa(codigoEmpresa,nombreEmpresa,usuarioResponsable);
                repositorioDeEmpresas.guardar(nuevaEmpresa);
                nuevoServicio.setEmpresa(nuevaEmpresa);

            } else{
                 Empresa empresaExistente = (Empresa) empresaXCodigo.get(0);
                 nuevoServicio.setEmpresa(empresaExistente);
            }
            List<Object> servicioXCodigo = repositorioDeServicios.buscarServicioXCodigo(codigoServicio);

            if(servicioXCodigo.isEmpty()){
                repositorioDeServicios.guardar(nuevoServicio);
            }

        }
    }
}
