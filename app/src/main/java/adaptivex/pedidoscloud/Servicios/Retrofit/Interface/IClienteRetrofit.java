package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.ClienteEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IClienteRetrofit {
    @GET(Configurador.urlClientes)
    Call<List<ClienteEntity>> getClientes(@Header("Authorization") String authorization);
}


