package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de horario y gudardarlos en la base local
 */

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Controller.HorarioController;
import adaptivex.pedidoscloud.Model.Horario;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IHorarioRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class HorarioServices {
    public List<Horario> horariosList;
    public Horario horario;

    private Context ctx;
    private HashMap<String, String> registro;
    private HorarioController horariosCtr;

    public HorarioServices() {
    }

    public HorarioServices(Context pCtx){
        this.setCtx(pCtx);
        this.horariosCtr = new HorarioController(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Horario> getHorarios(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IHorarioRetrofit service = retrofit.create(IHorarioRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Horario>> call = service.getHorarios("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Horario>>() {
            @Override
            public void onResponse(Call<List<Horario>> call, Response<List<Horario>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Horario: Error",String.valueOf(response.code()));
                    return;
                }
                horariosCtr.abrir().limpiar();
                horariosList = response.body();
                String content = "";
                for (Horario horario: horariosList){
                    horariosCtr.abrir().agregar(horario);
                }
                Log.println(Log.INFO,"Horario: ",content);
            }

            @Override
            public void onFailure(Call<List<Horario>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return horariosList;
    }
}
