package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IPedidoRetrofit {
    @GET(Configurador.urlParameters)
    Call<List<PedidoEntity>> getPedidos(@Header("Authorization") String authorization);
}


