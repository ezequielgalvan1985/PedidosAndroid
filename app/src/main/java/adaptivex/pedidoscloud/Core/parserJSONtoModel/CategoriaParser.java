package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import adaptivex.pedidoscloud.Entity.CategoriaEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.CategoriaDataBaseHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class CategoriaParser {
    private String jsonstr;
    private JSONObject jsonobj;
    private JSONArray data;
    private String status;
    private String message;
    private JSONObject categoriajson;
    private CategoriaEntity categoria;
    private ArrayList<CategoriaEntity> listadoCategorias;
    /* Solamente parsea los datos de un String Json, al Objeto CategoriaParser */
    public CategoriaParser(){
    }

    public CategoriaParser(String jsonstr){
        setJsonstr(jsonstr);
    }

    public CategoriaEntity parseJsonToObject(){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoCategorias  = new ArrayList<CategoriaEntity>();
            setJsonobj(new JSONObject(getJsonstr()));

            setStatus(getJsonobj().getString("code"));
            setMessage(getJsonobj().getString("message"));
            setData(getJsonobj().getJSONArray("data"));

            if (Integer.parseInt(getStatus())== 200){
                //parser Usuario
                JSONArray categorias = getData();
                CategoriaEntity categoria = new CategoriaEntity();
                for (int i = 0; i < categorias.length(); i++) {
                    JSONObject c = categorias.getJSONObject(i);

                    categoria.setId(c.getInt("id"));
                    categoria.setNombre(c.getString(CategoriaDataBaseHelper.CAMPO_NOMBRE));
                    categoria.setDescripcion(c.getString(CategoriaDataBaseHelper.CAMPO_DESCRIPCION));
                    listadoCategorias.add(categoria);
                    categoria =  new CategoriaEntity();

                }//endfor

            }else {
                Log.d("CategoriaParser: ", "Status: " + getStatus().toString());
            }
        }catch(Exception e ){
            Log.d("CategoriaParser: ", e.getMessage().toString());
        }

        return getCategoria();
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

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
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

    public JSONObject getCategoriajson() {
        return categoriajson;
    }

    public void setCategoriajson(JSONObject categoriajson) {
        this.categoriajson = categoriajson;
    }

    public ArrayList<CategoriaEntity> getListadoCategorias() {
        return listadoCategorias;
    }

    public void setListadoCategorias(ArrayList<CategoriaEntity> listadoCategorias) {
        this.listadoCategorias = listadoCategorias;
    }
}
