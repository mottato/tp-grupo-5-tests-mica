package ar.edu.utn.frba.dds.models.georef;



import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioGeoref {
    //creo un Singleton
    private static ServicioGeoref instancia = null;
    private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/"; //ruta absoluta
    private Retrofit retrofit;

    private ServicioGeoref() {
        this.retrofit = new Retrofit.Builder() //Builder ayuda a la construccion de un objeto
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref getInstancia() {
        if(instancia == null) {
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoDeMunicipios listadoDeMunicipios() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosArg = georefService.municipios();
        Response<ListadoDeMunicipios> responseMunicipiosArg = requestMunicipiosArg.execute();
        return  responseMunicipiosArg.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosDeProvincia(int idProvincia) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosDeProvincia = georefService.municipios(idProvincia,200);
        Response<ListadoDeMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();
        return responseMunicipiosDeProvincia.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosPorNombre(String nombre) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosPorNombre = georefService.municipios(nombre);
        Response<ListadoDeMunicipios> responseMunicipiosPorNombre = requestMunicipiosPorNombre.execute();
        return responseMunicipiosPorNombre.body();
    }

    public ListadoDeMunicipios listadoDeMunicipiosPorID(int idMunicipio) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosPorID = georefService.municipios(idMunicipio);
        Response<ListadoDeMunicipios> responseMunicipiosPorID = requestMunicipiosPorID.execute();
        return responseMunicipiosPorID.body();
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArg = georefService.provincias();
        Response<ListadoDeProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return  responseProvinciasArg.body();
    }

    public ListadoDeProvincias listadoDeProvinciasPorID(int id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArgPorID = georefService.provincias(id);
        Response<ListadoDeProvincias> responseProvinciasArgporID = requestProvinciasArgPorID.execute();
        return  responseProvinciasArgporID.body();
    }
}
