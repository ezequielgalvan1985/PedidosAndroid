package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.MarcaEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

import java.util.List;

public interface IMarcaRetrofit {
    @GET(Configurador.urlMarcas)
    Call<List<MarcaEntity>> getMarcas(@Header("Authorization") String authorization);
}


