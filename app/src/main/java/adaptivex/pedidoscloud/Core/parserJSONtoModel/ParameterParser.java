package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ParameterDataBaseHelper;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class ParameterParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONArray data;
    private String status;
    private String message;
    private JSONObject parameterjson;
    private ParameterEntity parameterEntity;
    private ArrayList<ParameterEntity> listadoParameterEntities;
    /* Solamente parsea los datos de un String Json, al Objeto ParameterParser */
    public ParameterParser(){
    }

    public ParameterParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public ParameterEntity parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoParameterEntities = new ArrayList<ParameterEntity>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray parameters = getData();
                ParameterEntity parameterEntity = new ParameterEntity();
                for (int i = 0; i < parameters.length(); i++) {
                    JSONObject c = parameters.getJSONObject(i);

                    if (c.has(ParameterDataBaseHelper.ID)){
                        parameterEntity.setId(c.getInt(ParameterDataBaseHelper.ID));
                    }
                    if (c.has(ParameterDataBaseHelper.NOMBRE)){
                        parameterEntity.setNombre(c.getString(ParameterDataBaseHelper.NOMBRE));
                    }
                    if (c.has(ParameterDataBaseHelper.DESCRIPCION)){
                        parameterEntity.setDescripcion(c.getString(ParameterDataBaseHelper.DESCRIPCION));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_TEXTO)){
                        parameterEntity.setValor_texto(c.getString(ParameterDataBaseHelper.VALOR_TEXTO));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_FECHA)){
                        parameterEntity.setValor_fecha(WorkDate.parseStringToDate(c.getString(ParameterDataBaseHelper.VALOR_FECHA)));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_DECIMAL)){
                        parameterEntity.setValor_decimal(c.getDouble(ParameterDataBaseHelper.VALOR_DECIMAL));
                    }
                    if (c.has(ParameterDataBaseHelper.VALOR_INTEGER)){
                        parameterEntity.setValor_integer(c.getInt(ParameterDataBaseHelper.VALOR_INTEGER));
                    }
                    listadoParameterEntities.add(parameterEntity);
                    parameterEntity =  new ParameterEntity();
                }//endfor

            }else {
                Log.d("ParameterParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("ParameterParser: ", e.getMessage().toString());
        }

        return getParameter();
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public ParameterEntity getParameter() {
        return parameterEntity;
    }

    public void setParameter(ParameterEntity parameterEntity) {
        this.parameterEntity = parameterEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getJsonobj() {
        return jsonobj;
    }

    public void setJsonobj(JSONObject jsonobj) {
        this.jsonobj = jsonobj;
    }

    public String getJsonstr() {
        return jsonstr;
    }

    public void setJsonstr(String jsonstr) {
        this.jsonstr = jsonstr;
    }

    public JSONObject getParameterjson() {
        return parameterjson;
    }

    public void setParameterjson(JSONObject parameterjson) {
        this.parameterjson = parameterjson;
    }

    public ArrayList<ParameterEntity> getListadoParameters() {
        return listadoParameterEntities;
    }

    public void setListadoParameters(ArrayList<ParameterEntity> listadoParameterEntities) {
        this.listadoParameterEntities = listadoParameterEntities;
    }
}
