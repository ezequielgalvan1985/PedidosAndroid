package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de horario y gudardarlos en la base local
 */

import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Entity.HorarioEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IHorarioRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class HorarioServices {
    public List<HorarioEntity> horariosList;

    public HorarioServices() {
    }


    public List<HorarioEntity> getHorarios(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IHorarioRetrofit service = retrofit.create(IHorarioRetrofit.class);
        Call<List<HorarioEntity>> call = service.getHorarios(GlobalValues.getInstancia().getAuthorization());
        call.enqueue(new Callback<List<HorarioEntity>>() {
            @Override
            public void onResponse(Call<List<HorarioEntity>> call, Response<List<HorarioEntity>> response) {
                try{
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Horario: Error",String.valueOf(response.code()));
                        return;
                    }
                    FactoryRepositories.getInstancia().getHorarioRepository().abrir().limpiar();
                    horariosList = response.body();
                    String content = "";
                    for (HorarioEntity horarioEntity : horariosList){
                        FactoryRepositories.getInstancia().getHorarioRepository().abrir().agregar(horarioEntity);
                        content = horarioEntity.getDia().toString() +" - " + horarioEntity.getObservaciones();
                        Log.println(Log.INFO,"Horario: ",content );
                    }
                }catch (Exception e){
                    Log.println(Log.ERROR,"Horario: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<HorarioEntity>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return horariosList;
    }
}
