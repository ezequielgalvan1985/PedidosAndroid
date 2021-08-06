package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.HorarioEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IHorarioRetrofit {
    @GET(Configurador.urlHorarios)
    Call<List<HorarioEntity>> getHorarios(@Header("Authorization") String authorization);
}


