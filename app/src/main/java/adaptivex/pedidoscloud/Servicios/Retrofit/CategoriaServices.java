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
import adaptivex.pedidoscloud.Repositories.CategoriaRepository;
import adaptivex.pedidoscloud.Entity.CategoriaEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.ICategoriaRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class CategoriaServices {
    public List<CategoriaEntity> categoriasList;
    public CategoriaEntity categoria;

    private Context ctx;
    private HashMap<String, String> registro;

    public CategoriaRepository getCategoriasCtr() {
        return categoriasCtr;
    }

    public void setCategoriasCtr(CategoriaRepository categoriasCtr) {
        this.categoriasCtr = categoriasCtr;
    }

    private CategoriaRepository categoriasCtr;

    public CategoriaServices() {
    }

    public CategoriaServices(Context pCtx){
        this.setCtx(pCtx);
        this.setCategoriasCtr(new CategoriaRepository(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<CategoriaEntity> getCategorias(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ICategoriaRetrofit service = retrofit.create(ICategoriaRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        //Call<List<Categoria>> call = service.getCategorias("Basic YWRtaW4yOjEyMzQ=");

        Call<List<CategoriaEntity>> call = service.getCategorias(GlobalValues.getINSTANCIA().getAuthorization());
        call.enqueue(new Callback<List<CategoriaEntity>>() {
            @Override
            public void onResponse(Call<List<CategoriaEntity>> call, Response<List<CategoriaEntity>> response) {
                try{

                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Categoria: Error",String.valueOf(response.code()));
                        return;
                    }
                    FactoryRepositories.getInstancia().getCategoriaRepository().abrir().limpiar();
                    categoriasList = response.body();
                    String content = "";
                    for (CategoriaEntity categoria: categoriasList){
                        FactoryRepositories.getInstancia().getCategoriaRepository().abrir().agregar(categoria);
                        content = categoria.getId() + " " +categoria.getNombre() + " " + categoria.getDescripcion();
                        Log.println(Log.INFO,"Categoria: ",content);
                    }
                }catch (Exception e){
                    Log.println(Log.ERROR,"Categoria:",e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<List<CategoriaEntity>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return categoriasList;
    }
}
