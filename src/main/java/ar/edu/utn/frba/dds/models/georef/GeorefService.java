package ar.edu.utn.frba.dds.models.georef;


import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeMunicipios;
import ar.edu.utn.frba.dds.models.georef.entities.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {
    //solamente tenemos q modelar la ruta relativa, la absoluta va en otra parte
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("id") int id);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios();

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("nombre") String nombre);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("id") int idMunicipio);

    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("max") int max);
    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int idProvincia, @Query("campos") String campos, @Query("max") int max);
}
