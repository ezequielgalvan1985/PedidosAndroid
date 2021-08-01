package adaptivex.pedidoscloud.Servicios.Retrofit;

import static adaptivex.pedidoscloud.Config.Configurador.*;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.LoginData;
import adaptivex.pedidoscloud.Model.LoginResult;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface IUserRetrofit {
    @GET(Configurador.urlUsers)
    Call<List<MUserRetrofit>> getUsers();

    @POST(Configurador.urlPostLogin)
    Call<LoginResult> getLoginToken(@Body LoginData body);

}


