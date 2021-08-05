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
import adaptivex.pedidoscloud.Controller.ProductoController;
import adaptivex.pedidoscloud.Model.Producto;
import adaptivex.pedidoscloud.Servicios.Retrofit.Interface.IProductoRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class ProductoServices {
    public List<Producto> productosList;
    public Producto producto;

    private Context ctx;
    private HashMap<String, String> registro;

    public ProductoController getProductosCtr() {
        return productosCtr;
    }

    public void setProductosCtr(ProductoController productosCtr) {
        this.productosCtr = productosCtr;
    }

    private ProductoController productosCtr;

    public ProductoServices() {
    }

    public ProductoServices(Context pCtx){
        this.setCtx(pCtx);
        this.setProductosCtr(new ProductoController(getCtx()));
    }

    private void setCtx(Context pCtx) {
        this.ctx= pCtx;
    }

    public Context getCtx() {
        return ctx;
    }

    public List<Producto> getProductos(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Configurador.urlBase)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IProductoRetrofit service = retrofit.create(IProductoRetrofit.class);
        //UserSessionLogin.getINSTANCIA().getUser().setToken("5d3b54d7422aa18506d26656bd93a0db5e4fcc6c");
        Call<List<Producto>> call = service.getProductos("Basic YWRtaW4yOjEyMzQ=");
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if(!response.isSuccessful()){
                    Log.println(Log.INFO,"Producto: Error",String.valueOf(response.code()));
                    return;
                }
                getProductosCtr().abrir().limpiar();
                productosList = response.body();
                String content = "";
                for (Producto producto: productosList){
                    productosCtr.abrir().add(producto);
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
