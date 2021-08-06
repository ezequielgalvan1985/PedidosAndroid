package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.LoginData;
import adaptivex.pedidoscloud.Entity.LoginResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface IUserRetrofit {
    @GET(Configurador.urlUsers)
    Call<List<LoginData>> getUsers();

    @POST(Configurador.urlPostLogin)
    Call<LoginResult> getLoginToken(@Body LoginData body);

}


