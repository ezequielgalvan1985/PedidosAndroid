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
import adaptivex.pedidoscloud.Controller.UnidadmedidaController;
import adaptivex.pedidoscloud.Model.Unidadmedida;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IUnidadmedidaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class UnidadmedidaServices {
    public List<Unidadmedida> unidadmedidasList;
    public Unidadmedida unidadmedida;

    private Context ctx;
    private HashMap<String, String> registro;
    private UnidadmedidaController unidadmedidasCtr;

    public UnidadmedidaController getUnidadmedidasCtr() {
        return unidadmedidasCtr;
    }

    public void setUnidadmedidasCtr(UnidadmedidaController unidadmedidasCtr) {
        this.unidadmedidasCtr = unidadmedidasCtr;
    }

    public UnidadmedidaServices() {
    }

    public UnidadmedidaServices(Context pCtx){
        this.setCtx(pCtx);
        this.setUnidadmedidasCtr(new UnidadmedidaController(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Unidadmedida> getUnidadmedidas(){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IUnidadmedidaRetrofit service = retrofit.create(IUnidadmedidaRetrofit.class);
            //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
            Call<List<Unidadmedida>> call = service.getUnidadmedidas("Basic YWRtaW4yOjEyMzQ=");
            call.enqueue(new Callback<List<Unidadmedida>>() {
                @Override
                public void onResponse(Call<List<Unidadmedida>> call, Response<List<Unidadmedida>> response) {
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Unidadmedida: Error",String.valueOf(response.code()));
                        return;
                    }
                    getUnidadmedidasCtr().abrir().limpiar();
                    unidadmedidasList = response.body();
                    String content = "";
                    for (Unidadmedida unidadmedida: unidadmedidasList){
                        //Recorrer Lista
                        getUnidadmedidasCtr().abrir().agregar(unidadmedida);
                        content = unidadmedida.getId() + " " +unidadmedida.getNombre() + " " + unidadmedida.getDescripcion();
                        Log.println(Log.INFO,"Unidadmedida: ",content);
                    }
                }

                @Override
                public void onFailure(Call<List<Unidadmedida>> call, Throwable t) {
                    Log.println(Log.ERROR,"Codigo: ",t.getMessage());
                }
            });
            return unidadmedidasList;
        }catch (Exception e){
            Log.println(Log.ERROR,"Unidadmedida services",e.getMessage());
            return null;
        }
    }
}
