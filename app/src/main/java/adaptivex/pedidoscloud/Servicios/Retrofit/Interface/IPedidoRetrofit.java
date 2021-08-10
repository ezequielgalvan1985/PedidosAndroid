package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.LoginData;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IPedidoRetrofit {
    @GET(Configurador.urlPedidos)
    Call<List<PedidoEntity>> getPedidos(@Header("Authorization") String authorization);

    @POST(Configurador.urlPostPedido)
    Call<PedidoEntity> postPedido(@Header("Authorization") String authorization,@Body PedidoEntity body );

}


