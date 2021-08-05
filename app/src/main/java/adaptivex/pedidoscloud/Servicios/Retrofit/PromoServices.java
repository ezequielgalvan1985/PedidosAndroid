package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de categoria y gudardarlos en la base local
 */

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Controller.PromoController;
import adaptivex.pedidoscloud.Model.Promo;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IPromoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class PromoServices {
    public List<Promo> promosList;
    public Promo promo;

    private Context ctx;
    private HashMap<String, String> registro;
    private PromoController promosCtr;

    public PromoServices() {
    }

    public PromoServices(Context pCtx){
        this.setCtx(pCtx);
        this.promosCtr = new PromoController(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Promo> getPromos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPromoRetrofit service = retrofit.create(IPromoRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Promo>> call = service.getPromos("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Promo>>() {
            @Override
            public void onResponse(Call<List<Promo>> call, Response<List<Promo>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Promo: Error",String.valueOf(response.code()));
                    return;
                }
                promosCtr.abrir().limpiar();
                promosList = response.body();
                String content = "";
                for (Promo promo: promosList){
                    content += promo.getId() + " " +promo.getNombre() + " " + promo.getDescripcion();
                    //Recorrer Lista
                    promosCtr.abrir().add(promo);
                }
                Log.println(Log.INFO,"Promo: ",content);
            }

            @Override
            public void onFailure(Call<List<Promo>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return promosList;
    }
}
