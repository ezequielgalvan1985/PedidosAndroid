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
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IParameterRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ParameterServices {
    public List<ParameterEntity> parametersList;
    public ParameterEntity parameterEntity;

    public ParameterServices() {
    }


    public List<ParameterEntity> getParameters(){

        FactoryRepositories.getInstancia().getParameterRepository().abrir().limpiar();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IParameterRetrofit service = retrofit.create(IParameterRetrofit.class);
        Call<List<ParameterEntity>> call = service.getParameters(GlobalValues.getInstancia().getAuthorization());
        call.enqueue(new Callback<List<ParameterEntity>>() {
            @Override
            public void onResponse(Call<List<ParameterEntity>> call, Response<List<ParameterEntity>> response) {
                try{
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Parameter: Error",String.valueOf(response.code()));
                        return;
                    }
                    parametersList = response.body();
                    String content = "";
                    for (ParameterEntity parameterEntity : parametersList){
                        content = parameterEntity.getId() + " " + parameterEntity.getNombre() + " " + parameterEntity.getDescripcion();
                        //Recorrer Lista
                        FactoryRepositories.getInstancia().getParameterRepository().abrir().agregar(parameterEntity);
                        Log.println(Log.INFO,"Parameter: ",content);
                    }
                }catch (Exception e){
                    Log.println(Log.ERROR,"Parameter: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<ParameterEntity>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return parametersList;
    }
}
