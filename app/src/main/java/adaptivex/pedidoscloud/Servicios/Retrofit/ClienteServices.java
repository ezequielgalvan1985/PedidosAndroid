package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de cliente y gudardarlos en la base local
 */

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.ClienteRepository;
import adaptivex.pedidoscloud.Entity.ClienteEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IClienteRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ClienteServices {
    public List<ClienteEntity> clientesList;
    public ClienteEntity clienteEntity;

    private Context ctx;
    private HashMap<String, String> registro;
    private ClienteRepository clientesCtr;

    public ClienteServices() {
    }

    public ClienteServices(Context pCtx){
        this.setCtx(pCtx);
        this.clientesCtr = new ClienteRepository(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<ClienteEntity> getClientes(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IClienteRetrofit service = retrofit.create(IClienteRetrofit.class);
        //UserSessionLogin.getInstancia().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<ClienteEntity>> call = service.getClientes(GlobalValues.getInstancia().getAuthorization());
        call.enqueue(new Callback<List<ClienteEntity>>() {
            @Override
            public void onResponse(Call<List<ClienteEntity>> call, Response<List<ClienteEntity>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Cliente: Error",String.valueOf(response.code()));
                    return;
                }
                clientesCtr.abrir().limpiar();
                clientesList = response.body();
                String content = "";
                for (ClienteEntity clienteEntity : clientesList){
                    clientesCtr.abrir().agregar(clienteEntity);
                }
                Log.println(Log.INFO,"Cliente: ",content);
            }

            @Override
            public void onFailure(Call<List<ClienteEntity>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return clientesList;
    }
}
