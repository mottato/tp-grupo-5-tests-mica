package ar.edu.utn.frba.dds.models;


import ar.edu.utn.frba.dds.models.georef.ServicioGeoref;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.models.georef.entities.Municipio;

import java.io.IOException;
import java.util.Scanner;


public class EjemplosDeUsoApi {

    public static void main(String[] args) throws IOException {
        ServicioGeoref servicioGeoref = ServicioGeoref.getInstancia();

        System.out.println("Escribe el ID de la provincia para filtrar los municipios");

        Scanner entradaScanner = new Scanner(System.in);
        int idProvinciaElegida = Integer.parseInt(entradaScanner.nextLine()); //siempre lo que me viene por pantalla lo
        // interpreta como String por eso lo tengo que castear

        ListadoDeMunicipios municipiosDeProvincia = servicioGeoref.listadoDeMunicipiosDeProvincia(idProvinciaElegida);

        for(Municipio municipio: municipiosDeProvincia.municipios) {
            System.out.println(municipio.nombre + " lat: " + municipio.centroide.lat + " lon: " + municipio.centroide.lon);
        }

        ListadoDeMunicipios municipios = servicioGeoref.listadoDeMunicipios();
        for(Municipio municipio: municipios.municipios) {
            System.out.println(municipio.id + " " + municipio.nombre + " lat: " + municipio.centroide.lat + " lon: " + municipio.centroide.lon);
        }

        System.out.println("Escribe el nombre del municipio para obtener su localizacion");

        Scanner entradaScanner2 = new Scanner(System.in);
        String nombre = entradaScanner2.nextLine();

        ListadoDeMunicipios municipioElegido = servicioGeoref.listadoDeMunicipiosPorNombre(nombre);
        for(Municipio municipio: municipioElegido.municipios) {
            System.out.println(" latitud: " + municipio.centroide.lat + " longitud: " + municipio.centroide.lon);
        }
    }
}
