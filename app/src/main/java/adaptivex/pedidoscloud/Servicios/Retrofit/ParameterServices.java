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
import adaptivex.pedidoscloud.Controller.ParameterController;
import adaptivex.pedidoscloud.Model.Parameter;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IParameterRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ParameterServices {
    public List<Parameter> parametersList;
    public Parameter parameter;

    private Context ctx;
    private HashMap<String, String> registro;

    public ParameterController getParametersCtr() {
        return parametersCtr;
    }

    public void setParametersCtr(ParameterController parametersCtr) {
        this.parametersCtr = parametersCtr;
    }

    private ParameterController parametersCtr;

    public ParameterServices() {
    }

    public ParameterServices(Context pCtx){
        this.setCtx(pCtx);
        this.setParametersCtr(new ParameterController(pCtx));
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Parameter> getParameters(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IParameterRetrofit service = retrofit.create(IParameterRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Parameter>> call = service.getParameters("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Parameter>>() {
            @Override
            public void onResponse(Call<List<Parameter>> call, Response<List<Parameter>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Parameter: Error",String.valueOf(response.code()));
                    return;
                }
                getParametersCtr().abrir().limpiar();
                parametersList = response.body();
                String content = "";
                for (Parameter parameter: parametersList){
                    content = parameter.getId() + " " +parameter.getNombre() + " " + parameter.getDescripcion();
                    //Recorrer Lista
                    parametersCtr.abrir().agregar(parameter);
                    Log.println(Log.INFO,"Parameter: ",content);
                }

            }

            @Override
            public void onFailure(Call<List<Parameter>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return parametersList;
    }
}
