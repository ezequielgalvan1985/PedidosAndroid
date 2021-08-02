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
import adaptivex.pedidoscloud.Controller.PedidoController;
import adaptivex.pedidoscloud.Model.Pedido;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IPedidoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class PedidoServices {
    public List<Pedido> pedidosList;
    public Pedido pedido;

    private Context ctx;
    private HashMap<String, String> registro;
    private PedidoController pedidosCtr;

    public PedidoServices() {
    }

    public PedidoServices(Context pCtx){
        this.setCtx(pCtx);
        this.pedidosCtr = new PedidoController(this.getCtx());
    }

    private void setCtx(Context pCtx) {
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Pedido> getPedidos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPedidoRetrofit service = retrofit.create(IPedidoRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Pedido>> call = service.getPedidos("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Pedido>>() {
            @Override
            public void onResponse(Call<List<Pedido>> call, Response<List<Pedido>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Pedido: Error",String.valueOf(response.code()));
                    return;
                }
                pedidosCtr.abrir().limpiar();
                pedidosList = response.body();
                String content = "";
                for (Pedido pedido: pedidosList){
                    pedidosCtr.abrir().agregar(pedido);
                }
                Log.println(Log.INFO,"Pedido: ",content);
            }

            @Override
            public void onFailure(Call<List<Pedido>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return pedidosList;
    }
}
