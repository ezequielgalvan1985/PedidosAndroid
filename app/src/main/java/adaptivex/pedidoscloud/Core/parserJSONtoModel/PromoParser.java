package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Entity.PromoEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PromoDataBaseHelper;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class PromoParser {
    private String       jsonstr;
    private JSONObject   jsonobj;
    private JSONObject   respuesta;
    private String       status;
    private String       message;
    private JSONArray    data;
    private JSONObject   promojson;
    private PromoEntity promo;
    
    private ArrayList<PromoEntity> listadoPromos;
    /* Solamente parsea los datos de un String Json, al Objeto PromoParser */
    public PromoParser(){
    }

    public PromoParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public PromoEntity parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoPromos  = new ArrayList<PromoEntity>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray promos = getData();
                PromoEntity promo = new PromoEntity();
                for (int i = 0; i < promos.length(); i++) {
                    JSONObject registro = promos.getJSONObject(i);
                    promo.setId(registro.getInt("id"));
                    if (registro.has(PromoDataBaseHelper.CAMPO_ID)) promo.setId(registro.getInt(PromoDataBaseHelper.CAMPO_ID));
                    if (registro.has(PromoDataBaseHelper.CAMPO_NOMBRE)) promo.setNombre(registro.getString(PromoDataBaseHelper.CAMPO_NOMBRE)); else promo.setNombre("");
                    if (registro.has(PromoDataBaseHelper.CAMPO_DESCRIPCION)) promo.setDescripcion(registro.getString(PromoDataBaseHelper.CAMPO_DESCRIPCION)); else promo.setDescripcion("");
                    if (registro.has(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS)) promo.setCantKilos(registro.getInt(PromoDataBaseHelper.CAMPO_CANTIDAD_KILOS)); else promo.setCantKilos(0);
                    if (registro.has(PromoDataBaseHelper.CAMPO_ENABLED)) promo.setEnabled(registro.getBoolean(PromoDataBaseHelper.CAMPO_ENABLED)); else promo.setEnabled(false);
                    if (registro.has(PromoDataBaseHelper.CAMPO_FECHA_DESDE)) promo.setFechaDesde(registro.getString(PromoDataBaseHelper.CAMPO_FECHA_DESDE));
                    if (registro.has(PromoDataBaseHelper.CAMPO_FECHA_HASTA)) promo.setFechaHasta(registro.getString(PromoDataBaseHelper.CAMPO_FECHA_HASTA));
                    if (registro.has(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO)) promo.setImporteDescuento(registro.getDouble(PromoDataBaseHelper.CAMPO_IMPORTE_DESCUENTO)); else promo.setImporteDescuento(0.0);
                    if (registro.has(PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR)) promo.setPrecioAnterior(registro.getDouble(PromoDataBaseHelper.CAMPO_PRECIO_ANTERIOR)); else promo.setPrecioAnterior(0.0);
                    if (registro.has(PromoDataBaseHelper.CAMPO_PRECIO_PROMO)) promo.setPrecioPromo(registro.getDouble(PromoDataBaseHelper.CAMPO_PRECIO_PROMO)); else promo.setPrecioPromo(0.0);

                    if (registro.has(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO))       promo.setCantPoteCuarto(registro.getInt(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_CUARTO)); else promo.setCantPoteCuarto(0);
                    if (registro.has(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO))        promo.setCantPoteMedio(registro.getInt(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_MEDIO)); else promo.setCantPoteMedio(0);
                    if (registro.has(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO))   promo.setCantPoteTresCuarto(registro.getInt(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_TRESCUARTO)); else promo.setCantPoteTresCuarto(0);
                    if (registro.has(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO))         promo.setCantPoteKilo(registro.getInt(PromoDataBaseHelper.CAMPO_CANTIDAD_POTE_KILO)); else promo.setCantPoteKilo(0);

                    listadoPromos.add(promo);
                    promo = new PromoEntity();
                }//endfor

            }else {
                Log.d("PromoParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("PromoParser: ", e.getMessage().toString());
        }

        return getPromo();
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

    public PromoEntity getPromo() {
        return promo;
    }

    public void setPromo(PromoEntity promo) {
        this.promo = promo;
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

    public JSONObject getPromojson() {
        return promojson;
    }

    public void setPromojson(JSONObject promojson) {
        this.promojson = promojson;
    }

    public ArrayList<PromoEntity> getListadoPromos() {
        return listadoPromos;
    }

    public void setListadoPromos(ArrayList<PromoEntity> listadoPromos) {
        this.listadoPromos = listadoPromos;
    }
}
