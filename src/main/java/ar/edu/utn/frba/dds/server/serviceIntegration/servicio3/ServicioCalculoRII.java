package ar.edu.utn.frba.dds.server.serviceIntegration.servicio3;

import ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities.ExternoRequest;
import ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities.ExternoResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ServicioCalculoRII {

    private static ServicioCalculoRII instancia = null;
    private static final String urlAPI = "http://localhost:8080/"; //ruta absoluta
    private Retrofit retrofit;

    private ServicioCalculoRII() {
        this.retrofit = new Retrofit.Builder() //Builder ayuda a la construccion de un objeto
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioCalculoRII getInstancia() {
        if(instancia == null) {
            instancia = new ServicioCalculoRII();
        }
        return instancia;
    }


    public ExternoResponse listadoDeEntidadesResult(ExternoRequest entidadesRequest ) throws IOException {

        CalculoRIIService calculoRIIService = this.retrofit.create(CalculoRIIService.class);
        Call<ExternoResponse> entidades = calculoRIIService.entidades(entidadesRequest);
        Response<ExternoResponse> responseEntidades = entidades.execute();
        return  responseEntidades.body();
    }
}
