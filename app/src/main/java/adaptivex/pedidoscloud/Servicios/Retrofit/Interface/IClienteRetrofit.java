package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.Cliente;
import adaptivex.pedidoscloud.Model.Marca;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IClienteRetrofit {
    @GET(Configurador.urlClientes)
    Call<List<Cliente>> getClientes(@Header("Authorization") String authorization);
}


