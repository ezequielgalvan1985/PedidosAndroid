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
import adaptivex.pedidoscloud.Controller.CategoriaController;
import adaptivex.pedidoscloud.Model.Categoria;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.ICategoriaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class CategoriaServices {
    public List<Categoria> categoriasList;
    public Categoria categoria;

    private Context ctx;
    private HashMap<String, String> registro;
    private CategoriaController categoriasCtr;

    public CategoriaServices() {
    }

    public CategoriaServices(Context pCtx){
        this.setCtx(pCtx);
        this.categoriasCtr = new CategoriaController(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Categoria> getCategorias(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ICategoriaRetrofit service = retrofit.create(ICategoriaRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Categoria>> call = service.getCategorias("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Categoria: Error",String.valueOf(response.code()));
                    return;
                }
                categoriasCtr.abrir().limpiar();
                categoriasList = response.body();
                String content = "";
                for (Categoria categoria: categoriasList){
                    categoriasCtr.abrir().agregar(categoria);
                }
                Log.println(Log.INFO,"Categoria: ",content);
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return categoriasList;
    }
}
