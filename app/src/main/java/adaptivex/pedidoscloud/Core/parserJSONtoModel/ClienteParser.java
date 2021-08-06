package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Entity.ClienteEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ClienteDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class ClienteParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONObject respuesta;
    private String status;
    private String message;
    private JSONArray data;
    private JSONObject clientejson;
    private ClienteEntity clienteEntity;
    private ArrayList<ClienteEntity> listadoClienteEntities;
    /* Solamente parsea los datos de un String Json, al Objeto ClienteParser */
    public ClienteParser(){
    }

    public ClienteParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public ClienteEntity parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoClienteEntities = new ArrayList<ClienteEntity>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray clientes = getData();
                ClienteEntity clienteEntity = new ClienteEntity();
                for (int i = 0; i < clientes.length(); i++) {
                    JSONObject registro = clientes.getJSONObject(i);
                    clienteEntity.setId(registro.getInt("id"));
                    if (registro.has(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL)) clienteEntity.setRazonsocial(registro.getString(ClienteDataBaseHelper.CAMPO_RAZONSOCIAL)); else clienteEntity.setRazonsocial("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_CONTACTO)) clienteEntity.setContacto(registro.getString(ClienteDataBaseHelper.CAMPO_CONTACTO)); else clienteEntity.setContacto("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_NDOC)) clienteEntity.setNdoc(registro.getString(ClienteDataBaseHelper.CAMPO_NDOC)); else clienteEntity.setNdoc("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_DIRECCION)) clienteEntity.setDireccion(registro.getString(ClienteDataBaseHelper.CAMPO_DIRECCION)); else clienteEntity.setDireccion("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_TELEFONO)) clienteEntity.setTelefono(registro.getString(ClienteDataBaseHelper.CAMPO_TELEFONO)); else clienteEntity.setTelefono("");
                    if (registro.has(ClienteDataBaseHelper.CAMPO_EMAIL)) clienteEntity.setEmail(registro.getString(ClienteDataBaseHelper.CAMPO_EMAIL)); else clienteEntity.setEmail("");
                    listadoClienteEntities.add(clienteEntity);
                    clienteEntity = new ClienteEntity();
                }//endfor

            }else {
                Log.d("ClienteParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("ClienteParser: ", e.getMessage().toString());
        }

        return getCliente();
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

    public ClienteEntity getCliente() {
        return clienteEntity;
    }

    public void setCliente(ClienteEntity clienteEntity) {
        this.clienteEntity = clienteEntity;
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

    public JSONObject getClientejson() {
        return clientejson;
    }

    public void setClientejson(JSONObject clientejson) {
        this.clientejson = clientejson;
    }

    public ArrayList<ClienteEntity> getListadoClientes() {
        return listadoClienteEntities;
    }

    public void setListadoClientes(ArrayList<ClienteEntity> listadoClienteEntities) {
        this.listadoClienteEntities = listadoClienteEntities;
    }
}
