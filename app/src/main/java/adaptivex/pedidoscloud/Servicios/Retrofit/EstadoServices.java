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
import adaptivex.pedidoscloud.Core.FactoryRepositories;
import adaptivex.pedidoscloud.Repositories.EstadoRepository;
import adaptivex.pedidoscloud.Entity.EstadoEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IEstadoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class EstadoServices {
    public List<EstadoEntity> estadosList;
    public EstadoEntity estadoEntity;

    private Context ctx;
    private HashMap<String, String> registro;

    public EstadoRepository getEstadosCtr() {
        return estadosCtr;
    }

    public void setEstadosCtr(EstadoRepository estadosCtr) {
        this.estadosCtr = estadosCtr;
    }

    private EstadoRepository estadosCtr;

    public EstadoServices() {
    }

    public EstadoServices(Context pCtx){
        this.setCtx(pCtx);
        this.setEstadosCtr(new EstadoRepository(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<EstadoEntity> getEstados(){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IEstadoRetrofit service = retrofit.create(IEstadoRetrofit.class);
            Call<List<EstadoEntity>> call = service.getEstados(GlobalValues.getINSTANCIA().getAuthorization());
            call.enqueue(new Callback<List<EstadoEntity>>() {
                @Override
                public void onResponse(Call<List<EstadoEntity>> call, Response<List<EstadoEntity>> response) {
                    try{

                        if(!response.isSuccessful()){
                            Log.println(Log.INFO,"Estado: Error",String.valueOf(response.code()));
                            return;
                        }
                        FactoryRepositories.getInstancia().getEstadoRepository().abrir().limpiar();
                        estadosList = response.body();
                        String content = "";
                        for (EstadoEntity estadoEntity : estadosList){
                            //Recorrer Lista
                            FactoryRepositories.getInstancia().getEstadoRepository().abrir().agregar(estadoEntity);
                            content = estadoEntity.getId() + " " + estadoEntity.getNombre() + " " + estadoEntity.getDescripcion();
                            Log.println(Log.INFO,"Estado: ",content);
                        }
                    }catch (Exception e){
                        Log.println(Log.ERROR,"Estado: ",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<EstadoEntity>> call, Throwable t) {
                    Log.println(Log.ERROR,"Codigo: ",t.getMessage());
                }
            });
            return estadosList;
        }catch (Exception e){
            Log.println(Log.ERROR,"Error Estado services: ",e.getMessage());
            return null;
        }
    }
}
