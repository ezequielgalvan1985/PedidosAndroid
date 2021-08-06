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
import adaptivex.pedidoscloud.Core.FactoryRepositories;
import adaptivex.pedidoscloud.Entity.Producto;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IProductoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ProductoServices {
    public List<Producto> productosList;
    public Producto producto;

    public ProductoServices() {
    }


    public List<Producto> getProductos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IProductoRetrofit service = retrofit.create(IProductoRetrofit.class);
        Call<List<Producto>> call = service.getProductos(GlobalValues.getINSTANCIA().getAuthorization());
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Producto: Error",String.valueOf(response.code()));
                    return;
                }
                FactoryRepositories.getInstancia().getProductoRepository().abrir().limpiar();
                productosList = response.body();
                String content = "";
                for (Producto producto: productosList){
                    FactoryRepositories.getInstancia().getProductoRepository().abrir().add(producto);
                     Log.println(Log.INFO,"Producto: ",producto.getNombre().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {
                Log.println(Log.ERROR,"Codigo: ",t.getMessage());
            }
        });
        return productosList;
    }
}
