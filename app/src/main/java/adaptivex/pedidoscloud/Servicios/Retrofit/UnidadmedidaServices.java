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
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.UnidadmedidaRepository;
import adaptivex.pedidoscloud.Entity.UnidadmedidaEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IUnidadmedidaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class UnidadmedidaServices {
    public List<UnidadmedidaEntity> unidadmedidasList;
    public UnidadmedidaEntity unidadmedida;

    private Context ctx;
    private HashMap<String, String> registro;
    private UnidadmedidaRepository unidadmedidasCtr;

    public UnidadmedidaRepository getUnidadmedidasCtr() {
        return unidadmedidasCtr;
    }

    public void setUnidadmedidasCtr(UnidadmedidaRepository unidadmedidasCtr) {
        this.unidadmedidasCtr = unidadmedidasCtr;
    }

    public UnidadmedidaServices() {
    }

    public UnidadmedidaServices(Context pCtx){
        this.setCtx(pCtx);
        this.setUnidadmedidasCtr(new UnidadmedidaRepository(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<UnidadmedidaEntity> getUnidadmedidas(){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IUnidadmedidaRetrofit service = retrofit.create(IUnidadmedidaRetrofit.class);
            Call<List<UnidadmedidaEntity>> call = service.getUnidadmedidas(GlobalValues.getInstancia().getAuthorization());
            call.enqueue(new Callback<List<UnidadmedidaEntity>>() {
                @Override
                public void onResponse(Call<List<UnidadmedidaEntity>> call, Response<List<UnidadmedidaEntity>> response) {
                    try{


                        if(!response.isSuccessful()){
                            Log.println(Log.INFO,"Unidadmedida: Error",String.valueOf(response.code()));
                            return;
                        }
                        FactoryRepositories.getInstancia().getUnidadmedidaRepository().abrir().limpiar();
                        unidadmedidasList = response.body();
                        String content = "";
                        for (UnidadmedidaEntity unidadmedida: unidadmedidasList){
                            //Recorrer Lista
                            FactoryRepositories.getInstancia().getUnidadmedidaRepository().abrir().agregar(unidadmedida);
                            content = unidadmedida.getId() + " " +unidadmedida.getNombre() + " " + unidadmedida.getDescripcion();
                            Log.println(Log.INFO,"Unidadmedida: ",content);
                        }
                    }catch(Exception e){
                        Log.println(Log.ERROR,"Unidadmedida:",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<UnidadmedidaEntity>> call, Throwable t) {
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
