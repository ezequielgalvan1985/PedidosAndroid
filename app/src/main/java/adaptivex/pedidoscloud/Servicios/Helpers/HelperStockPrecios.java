package adaptivex.pedidoscloud.Servicios.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.ProductoRepository;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.ProductoParser;
import adaptivex.pedidoscloud.Entity.ProductoEntity;
import adaptivex.pedidoscloud.Servicios.WebRequest;

import java.util.HashMap;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperStockPrecios extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private ProductoEntity producto;
    private ProductoRepository productoCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Producto

    public HelperStockPrecios(Context pCtx){
        this.setCtx(pCtx);
        this.productoCtr = new ProductoRepository(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();

            String jsonStr = webreq.makeWebServiceCall(Configurador.urlProductos, WebRequest.POST,null);
            ProductoParser cp = new ProductoParser(jsonStr);
            cp.parseJsonToObject();
            productoCtr.abrir().limpiar();
            //Recorrer Lista
            for (int i = 0; i < cp.getListadoProductos().size(); i++) {
                //Validar, si existe producto, por codigo externo

                productoCtr.abrir().add(cp.getListadoProductos().get(i));
            }
            setRespuesta(GlobalValues.getInstancia().RETURN_OK);

        }catch (Exception e){
                setRespuesta(GlobalValues.getInstancia().RETURN_ERROR);
                Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog

    }


    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (getRespuesta()== GlobalValues.getInstancia().RETURN_OK){

        }
    }
    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }
}
