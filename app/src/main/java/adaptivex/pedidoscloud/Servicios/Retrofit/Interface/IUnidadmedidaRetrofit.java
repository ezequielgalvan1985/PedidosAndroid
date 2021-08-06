package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.Unidadmedida;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IUnidadmedidaRetrofit {
    @GET(Configurador.urlUnidadmedidas)
    Call<List<Unidadmedida>> getUnidadmedidas(@Header("Authorization") String authorization);
}


