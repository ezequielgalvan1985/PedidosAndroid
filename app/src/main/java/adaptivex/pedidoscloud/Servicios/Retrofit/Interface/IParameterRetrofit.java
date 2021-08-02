package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.Horario;
import adaptivex.pedidoscloud.Model.Parameter;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IParameterRetrofit {
    @GET(Configurador.urlParameters)
    Call<List<Parameter>> getParameters(@Header("Authorization") String authorization);
}


