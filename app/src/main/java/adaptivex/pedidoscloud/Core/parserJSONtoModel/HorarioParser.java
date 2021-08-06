package adaptivex.pedidoscloud.Core.parserJSONtoModel;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Entity.HorarioEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.HorarioDataBaseHelper;

/**
 * Created by ezequiel on 16/8/2017.
 */

public class HorarioParser {
    private HorarioEntity horarioEntity;
    private ArrayList<HorarioEntity> listadoHorarioEntities;
    /* Solamente parsea los datos de un String Json, al Objeto HorarioParser */


    public HorarioParser(){
    }

    public HorarioEntity parseJsonToObject(String textJson){
        /* Completa datos del objeto  */
        try{
            //leer raiz
            listadoHorarioEntities = new ArrayList<HorarioEntity>();
            JSONObject  jsonobject     =  new JSONObject(textJson);


            //parser Usuario
            JSONArray horarios = jsonobject.getJSONArray("data");
            HorarioEntity horarioEntity = new HorarioEntity();
            for (int i = 0; i < horarios.length(); i++) {
                JSONObject c = horarios.getJSONObject(i);

                JSONObject item = c.getJSONObject(HorarioDataBaseHelper.CAMPO_ROOT);

                horarioEntity.setId(item.getInt(HorarioDataBaseHelper.CAMPO_ID));
                horarioEntity.setDia(item.getInt(HorarioDataBaseHelper.CAMPO_DIA));

                JSONObject apertura = item.getJSONObject(HorarioDataBaseHelper.CAMPO_APERTURA);
                horarioEntity.setApertura(WorkDate.parseStringToTime(apertura.getString(HorarioDataBaseHelper.CAMPO_DATE)));

                JSONObject cierre = item.getJSONObject(HorarioDataBaseHelper.CAMPO_CIERRE);
                horarioEntity.setCierre(WorkDate.parseStringToTime(cierre.getString(HorarioDataBaseHelper.CAMPO_DATE)));

                horarioEntity.setObservaciones(item.getString(HorarioDataBaseHelper.CAMPO_OBSERVACIONES));

                listadoHorarioEntities.add(horarioEntity);
                horarioEntity =  new HorarioEntity();

            }//endfor
        }catch(Exception e ){
            Log.d("HorarioParser: ", e.getMessage().toString());
        }

        return getHorario();
    }




    public HorarioEntity getHorario() {
        return horarioEntity;
    }

    public void setHorario(HorarioEntity horarioEntity) {
        this.horarioEntity = horarioEntity;
    }





    public ArrayList<HorarioEntity> getListadoHorarios() {
        return listadoHorarioEntities;
    }

    public void setListadoHorarios(ArrayList<HorarioEntity> listadoHorarioEntities) {
        this.listadoHorarioEntities = listadoHorarioEntities;
    }
}
