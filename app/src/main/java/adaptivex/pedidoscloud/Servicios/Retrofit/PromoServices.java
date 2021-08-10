package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de categoria y gudardarlos en la base local
 */

import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Entity.PromoEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IPromoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class PromoServices {
    public List<PromoEntity> promosList;

    public PromoServices() {
    }

    public List<PromoEntity> getPromos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPromoRetrofit service = retrofit.create(IPromoRetrofit.class);
        Call<List<PromoEntity>> call = service.getPromos(GlobalValues.getInstancia().getAuthorization());
        call.enqueue(new Callback<List<PromoEntity>>() {
            @Override
            public void onResponse(Call<List<PromoEntity>> call, Response<List<PromoEntity>> response) {
                try{
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Promo: Error",String.valueOf(response.code()));
                        return;
                    }
                    FactoryRepositories.getInstancia().getPromoRepository().abrir().limpiar();
                    promosList = response.body();
                    String content = "";
                    for (PromoEntity promo: promosList){
                        content += promo.getId() + " " +promo.getNombre() + " " + promo.getDescripcion();
                        //Recorrer Lista
                        FactoryRepositories.getInstancia().getPromoRepository().abrir().add(promo);
                    }
                    Log.println(Log.INFO,"Promo: ",content);
                }catch(Exception e){
                    Log.println(Log.INFO,"Promo: Error",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<PromoEntity>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return promosList;
    }
}
