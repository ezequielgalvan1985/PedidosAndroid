package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IUserProfileRetrofit {
    @GET(Configurador.urlUserProfile)
    Call<List<UserProfileEntity>> getUserProfiles(@Header("Authorization") String authorization);

}


