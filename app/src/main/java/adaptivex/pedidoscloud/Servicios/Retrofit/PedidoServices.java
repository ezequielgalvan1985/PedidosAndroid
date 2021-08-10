package adaptivex.pedidoscloud.Servicios.Retrofit;

/**
 * Created by Ezequiel on 01/08/2021.
 * Proposito:
 * descargar todos los registros de categoria y gudardarlos en la base local
 */

import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.FactoryRepositories;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IPedidoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class PedidoServices {
    public List<PedidoEntity> pedidosList;
    public PedidoEntity pedido;

    public PedidoServices() {
    }

    public List<PedidoEntity> getPedidos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IPedidoRetrofit service = retrofit.create(IPedidoRetrofit.class);
        Call<List<PedidoEntity>> call = service.getPedidos(GlobalValues.getInstancia().getAuthorization());
        call.enqueue(new Callback<List<PedidoEntity>>() {
            @Override
            public void onResponse(Call<List<PedidoEntity>> call, Response<List<PedidoEntity>> response) {
                try{


                    if(!response.isSuccessful()){
                        Log.println(Log.INFO,"Pedido: Error",String.valueOf(response.code()));
                        return;
                    }
                    FactoryRepositories.getInstancia().getPedidoRepository().abrir().limpiar();
                    pedidosList = response.body();
                    String content = "";
                    for (PedidoEntity pedido: pedidosList){
                        FactoryRepositories.getInstancia().getPedidoRepository().abrir().agregar(pedido);
                    }
                    Log.println(Log.INFO,"Pedido: ",content);
                }catch(Exception e){
                    Log.println(Log.ERROR,"Pedido: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<PedidoEntity>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return pedidosList;
    }


    public void postPedido(PedidoEntity pPedido){
        try{
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Configurador.urlBase)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            IPedidoRetrofit service = retrofit.create(IPedidoRetrofit.class);

            Call<PedidoEntity> call =
                    service.postPedido(
                            GlobalValues.getInstancia().getAuthorization(),
                            pPedido);

            call.enqueue(
                    new Callback<PedidoEntity>() {
                        @Override
                        public void onResponse(Call<PedidoEntity> call, Response<PedidoEntity> response) {
                            if(!response.isSuccessful()){
                                Log.println(Log.ERROR,"PedidoServices",String.valueOf(response.code()));
                                return;
                            }
                        }

                        @Override
                        public void onFailure(Call<PedidoEntity> call, Throwable t) {
                            Log.println(Log.ERROR,"Pedidoservices: ",t.getMessage());
                        }
                    }
            );


        }catch(Exception e){
            Log.e("pedidoservices",e.getMessage());
        }
    }
}
