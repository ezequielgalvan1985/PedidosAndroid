package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.LoginData;
import adaptivex.pedidoscloud.Model.LoginResult;
import adaptivex.pedidoscloud.Model.UserProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IUserProfileRetrofit {
    @GET(Configurador.urlUserProfile)
    Call<List<UserProfile>> getUserProfiles(@Header("Authorization") String authorization);

}


