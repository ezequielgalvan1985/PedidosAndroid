package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.CategoriaEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ICategoriaRetrofit {
    @GET(Configurador.urlCategorias)
    Call<List<CategoriaEntity>> getCategorias(@Header("Authorization") String authorization);
}
