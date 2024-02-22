package ar.edu.utn.frba.dds.server.serviceIntegration.servicio3;

import ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities.ExternoRequest;
import ar.edu.utn.frba.dds.server.serviceIntegration.servicio3.entities.ExternoResponse;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.POST;

//Servicio 3: cálculo de ranking de impacto de incidentes
public interface CalculoRIIService {
    @POST("calculate")
    Call<ExternoResponse> entidades(@Body ExternoRequest entidadesRequest);

}
