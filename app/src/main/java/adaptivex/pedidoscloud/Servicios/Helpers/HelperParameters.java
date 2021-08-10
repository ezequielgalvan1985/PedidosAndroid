package adaptivex.pedidoscloud.Servicios.Helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.HashMap;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Config.Constants;
import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Repositories.ParameterRepository;
import adaptivex.pedidoscloud.Core.parserJSONtoModel.ParameterParser;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Servicios.WebRequest;

/**
 * Created by Ezequiel on 02/04/2017.
 */

public class HelperParameters extends AsyncTask<Void, Void, Void> {
    private Context ctx;
    private HashMap<String,String> registro;
    private ParameterEntity parameterEntity;
    private ParameterRepository parameterCtr;
    private int respuesta; //1=ok, 200=error


    private String TEXT_RESPONSE;
    private int CURRENT_OPTION = 0; //1 enviar Post Parameter


    public static final int OPTION_ALL = 1;
    public static final int OPTION_ONLY_PRICE = 2;

    public HelperParameters(Context pCtx){
        this.setCtx(pCtx);
        this.parameterCtr = new ParameterRepository(this.getCtx());
    }

    private void findAll(){
        try{
            WebRequest webreq = new WebRequest();
            TEXT_RESPONSE = webreq.makeWebServiceCall(Configurador.urlParameters, WebRequest.POST, null);
        }catch (Exception e){
            setRespuesta(GlobalValues.getInstancia().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }

    private void findOnlyPrices(){
        try{
            WebRequest webreq = new WebRequest();
            TEXT_RESPONSE = webreq.makeWebServiceCall(Configurador.urlParameters, WebRequest.POST, null);
        }catch (Exception e){
            setRespuesta(GlobalValues.getInstancia().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }

    private void setPricesGlobalValues(){
        try{
            //Actulizar variables del sistema con los valores de precio
            ParameterRepository parameterCtr = new ParameterRepository(getCtx());
            ParameterEntity p;
            p = parameterCtr.abrir().findByNombre(GlobalValues.getInstancia().PARAM_PRECIOXKILO);
            Constants.PRECIO_HELADO_KILO = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getInstancia().PARAM_PRECIOXMEDIO);
            Constants.PRECIO_HELADO_MEDIO = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getInstancia().PARAM_PRECIOXCUARTO);
            Constants.PRECIO_HELADO_CUARTO = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getInstancia().PARAM_PRECIOTRESCUARTOS);
            Constants.PRECIO_HELADO_TRESCUARTOS = p.getValor_decimal();

            p = parameterCtr.abrir().findByNombre(GlobalValues.getInstancia().PARAM_PRECIOCUCURUCHO);
            Constants.PRECIO_CUCURUCHO = p.getValor_decimal();
        }catch (Exception e){
            setRespuesta(GlobalValues.getInstancia().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
        }
    }

    private boolean onPostFindAll(){
        try{
            ParameterParser cp = new ParameterParser(TEXT_RESPONSE);
            cp.parseJsonToObject();
            //parameterCtr.abrir().limpiar();
            for (int i = 0; i < cp.getListadoParameters().size(); i++) {

                ParameterEntity parameter_Entity_server = cp.getListadoParameters().get(i);
                ParameterEntity parameter_Entity_local = parameterCtr.abrir().findByNombre(cp.getListadoParameters().get(i).getNombre());
                if (parameter_Entity_local ==null){
                    parameterCtr.abrir().agregar(parameter_Entity_server);
                }else{
                    parameterCtr.abrir().modificar(parameter_Entity_server);
                }
            }
            setPricesGlobalValues();
            setRespuesta(GlobalValues.getInstancia().RETURN_OK);
            return true;
        }catch (Exception e){
            setRespuesta(GlobalValues.getInstancia().RETURN_ERROR);
            Log.println(Log.ERROR,"ErrorHelper:",e.getMessage());
            return false;
        }
    }







    @Override
    protected Void doInBackground(Void... voids) {
        switch (this.getCURRENT_OPTION()){
            case OPTION_ALL:
                findAll();
                break;
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
        switch (getCURRENT_OPTION()){
            case OPTION_ALL:
                onPostFindAll();
                break;
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


    public int getCURRENT_OPTION() {
        return CURRENT_OPTION;
    }

    public void setCURRENT_OPTION(int CURRENT_OPTION) {
        this.CURRENT_OPTION = CURRENT_OPTION;
    }
}
