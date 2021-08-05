package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.Estado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IEstadoRetrofit {
    @GET(Configurador.urlEstados)
    Call<List<Estado>> getEstados(@Header("Authorization") String authorization);
}


