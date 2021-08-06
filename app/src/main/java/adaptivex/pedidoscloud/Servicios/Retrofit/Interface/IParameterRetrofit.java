package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IParameterRetrofit {
    @GET(Configurador.urlParameters)
    Call<List<ParameterEntity>> getParameters(@Header("Authorization") String authorization);
}


