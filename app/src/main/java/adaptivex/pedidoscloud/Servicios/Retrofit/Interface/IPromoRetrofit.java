package adaptivex.pedidoscloud.Servicios.Retrofit.Interface;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Entity.Promo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IPromoRetrofit {
    @GET(Configurador.urlParameters)
    Call<List<Promo>> getPromos(@Header("Authorization") String authorization);
}


