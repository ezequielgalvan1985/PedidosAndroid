package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class MarcaParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject marcajson;
    private MarcaEntity marcaEntity;
    private ArrayList<MarcaEntity> listadoMarcaEntities;
    /* Solamente parsea los datos de un String Json, al Objeto MarcaParser */
    public MarcaParser(){
    }

    public MarcaParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public MarcaEntity parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoMarcaEntities = new ArrayList<MarcaEntity>();
            setJsonobj(new JSONObject(getJsonstr()));
            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));
            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray marcas = getData();
                MarcaEntity marcaEntity = new MarcaEntity();
                for (int i = 0; i < marcas.length(); i++) {
                    JSONObject registro = marcas.getJSONObject(i);

                    marcaEntity.setId(registro.getInt("id"));
                    marcaEntity.setNombre(registro.getString(MarcaDataBaseHelper.CAMPO_NOMBRE));
                    marcaEntity.setDescripcion(registro.getString(MarcaDataBaseHelper.CAMPO_DESCRIPCION));
                    listadoMarcaEntities.add(marcaEntity);
                    marcaEntity = new MarcaEntity();
                }//endfor

            }else {
                Log.d("MarcaParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("MarcaParser: ", e.getMessage().toString());
        }

        return getMarca();
    }
    public JSONObject getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(JSONObject respuesta) {
        this.respuesta = respuesta;
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

    public MarcaEntity getMarca() {
        return marcaEntity;
    }

    public void setMarca(MarcaEntity marcaEntity) {
        this.marcaEntity = marcaEntity;
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

    public JSONObject getMarcajson() {
        return marcajson;
    }

    public void setMarcajson(JSONObject marcajson) {
        this.marcajson = marcajson;
    }

    public ArrayList<MarcaEntity> getListadoMarcas() {
        return listadoMarcaEntities;
    }

    public void setListadoMarcas(ArrayList<MarcaEntity> listadoMarcaEntities) {
        this.listadoMarcaEntities = listadoMarcaEntities;
    }
}
