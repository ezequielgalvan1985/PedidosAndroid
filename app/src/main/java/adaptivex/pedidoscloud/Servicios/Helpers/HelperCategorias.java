package adaptivex.pedidoscloud.Servicios.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.CategoriaRepository;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.CategoriaParser;
import adaptivex.pedidoscloud.Entity.CategoriaEntity;
import adaptivex.pedidoscloud.Servicios.WebRequest;

import java.util.HashMap;

/**
 * Created by Ezequiel on 02/04/2017.
 * Proposito:
 * descargar todos los registros de categoria y gudardarlos en la base local
 */

public class HelperCategorias extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private CategoriaEntity categoria;
    private CategoriaRepository categoriaCtr;
    private int respuesta; //1=ok, 200=error
    private int opcion; //1 enviar Post Categoria
    private String jsonStr ="";
    public HelperCategorias(Context pCtx){
        this.setCtx(pCtx);
        this.categoriaCtr = new CategoriaRepository(this.getCtx());
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try{
            WebRequest webreq = new WebRequest();
            registro = new HashMap<String, String>();
            jsonStr = webreq.makeWebServiceCall(Configurador.urlCategorias, WebRequest.POST,registro);

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
            CategoriaParser cp = new CategoriaParser(jsonStr);
            cp.parseJsonToObject();
            categoriaCtr.abrir().limpiar();
            //Recorrer Lista
            for (int i = 0; i < cp.getListadoCategorias().size(); i++) {
                categoriaCtr.abrir().agregar(cp.getListadoCategorias().get(i));
            }
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
