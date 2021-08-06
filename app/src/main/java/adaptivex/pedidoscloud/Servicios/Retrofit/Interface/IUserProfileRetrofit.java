package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.UserProfile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IUserProfileRetrofit {
    @GET(Configurador.urlUserProfile)
    Call<List<UserProfile>> getUserProfiles(@Header("Authorization") String authorization);

}


