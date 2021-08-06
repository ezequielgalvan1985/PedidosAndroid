package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.Producto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IProductoRetrofit {
    @GET(Configurador.urlProductos)
    Call<List<Producto>> getProductos(@Header("Authorization") String authorization);
}


