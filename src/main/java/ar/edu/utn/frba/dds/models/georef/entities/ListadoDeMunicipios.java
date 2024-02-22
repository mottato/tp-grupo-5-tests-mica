package ar.edu.utn.frba.dds.models.georef.entities;

import java.util.List;

public class ListadoDeMunicipios {
    public int cantidad;
    public int inicio;
    public int total;
    public List<Municipio> municipios;
    public Parametro parametro;

    private class Parametro {
        public List<String> campos;
        public int max;
        public List<String> provincia;

    }

    public ListadoDeMunicipios() {
    }
}
