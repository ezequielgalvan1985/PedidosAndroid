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
import adaptivex.pedidoscloud.Controller.EstadoController;
import adaptivex.pedidoscloud.Model.Estado;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IEstadoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class EstadoServices {
    public List<Estado> estadosList;
    public Estado estado;

    private Context ctx;
    private HashMap<String, String> registro;

    public EstadoController getEstadosCtr() {
        return estadosCtr;
    }

    public void setEstadosCtr(EstadoController estadosCtr) {
        this.estadosCtr = estadosCtr;
    }

    private EstadoController estadosCtr;

    public EstadoServices() {
    }

    public EstadoServices(Context pCtx){
        this.setCtx(pCtx);
        this.setEstadosCtr(new EstadoController(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Estado> getEstados(){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IEstadoRetrofit service = retrofit.create(IEstadoRetrofit.class);
            //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
            Call<List<Estado>> call = service.getEstados("Basic YWRtaW4yOjEyMzQ=");
            call.enqueue(new Callback<List<Estado>>() {
                @Override
                public void onResponse(Call<List<Estado>> call, Response<List<Estado>> response) {
                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Estado: Error",String.valueOf(response.code()));
                        return;
                    }
                    getEstadosCtr().abrir().limpiar();
                    estadosList = response.body();
                    String content = "";
                    for (Estado estado: estadosList){
                        //Recorrer Lista
                        getEstadosCtr().abrir().agregar(estado);
                        content = estado.getId() + " " +estado.getNombre() + " " + estado.getDescripcion();
                        Log.println(Log.INFO,"Estado: ",content);
                    }
                }

                @Override
                public void onFailure(Call<List<Estado>> call, Throwable t) {
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
