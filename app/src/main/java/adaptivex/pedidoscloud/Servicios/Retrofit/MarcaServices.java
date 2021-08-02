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
import adaptivex.pedidoscloud.Controller.MarcaController;
import adaptivex.pedidoscloud.Model.Marca;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IMarcaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class MarcaServices {
    public List<Marca> marcasList;
    public Marca marca;

    private Context ctx;
    private HashMap<String, String> registro;
    private MarcaController marcasCtr;

    public MarcaServices() {
    }

    public MarcaServices(Context pCtx){
        this.setCtx(pCtx);
        this.marcasCtr = new MarcaController(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Marca> getMarcas(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMarcaRetrofit service = retrofit.create(IMarcaRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Marca>> call = service.getMarcas("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Marca: Error",String.valueOf(response.code()));
                    return;
                }
                marcasCtr.abrir().limpiar();
                marcasList = response.body();
                String content = "";
                for (Marca marca: marcasList){
                    content += marca.getId() + " " +marca.getNombre() + " " + marca.getDescripcion();
                    //Recorrer Lista
                    marcasCtr.abrir().agregar(marca);
                }
                Log.println(Log.INFO,"Marca: ",content);
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return marcasList;
    }
}
