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
import adaptivex.pedidoscloud.Core.FactoryRepositories;
import adaptivex.pedidoscloud.Entity.Promo;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IPromoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class PromoServices {
    public List<Promo> promosList;

    public PromoServices() {
    }

    public List<Promo> getPromos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPromoRetrofit service = retrofit.create(IPromoRetrofit.class);
        Call<List<Promo>> call = service.getPromos(GlobalValues.getINSTANCIA().getAuthorization());
        call.enqueue(new Callback<List<Promo>>() {
            @Override
            public void onResponse(Call<List<Promo>> call, Response<List<Promo>> response) {
                try{
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Promo: Error",String.valueOf(response.code()));
                        return;
                    }
                    FactoryRepositories.getInstancia().getPromoRepository().abrir().limpiar();
                    promosList = response.body();
                    String content = "";
                    for (Promo promo: promosList){
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
            public void onFailure(Call<List<Promo>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return promosList;
    }
}
