package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.Horario;
import adaptivex.pedidoscloud.Model.Pedido;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IPedidoRetrofit {
    @GET(Configurador.urlParameters)
    Call<List<Pedido>> getPedidos(@Header("Authorization") String authorization);
}


