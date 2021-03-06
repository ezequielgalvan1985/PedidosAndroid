package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import java.util.ArrayList;

import adaptivex.pedidoscloud.Core.WorkDate;
import adaptivex.pedidoscloud.Entity.HorarioEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.HorarioDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class HorarioRepository
{
    private Context context;
    private HorarioDataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public HorarioRepository(Context context)
    {
        this.context = context;
    }

    public HorarioRepository abrir() throws SQLiteException
    {
        dbHelper = new HorarioDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    public long agregar(HorarioEntity item)
    {
        ContentValues valores = new ContentValues();

        valores.put(HorarioDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(HorarioDataBaseHelper.CAMPO_DIA, item.getDia());
        valores.put(HorarioDataBaseHelper.CAMPO_APERTURA, WorkDate.parseDateToString(item.getApertura()));
        valores.put(HorarioDataBaseHelper.CAMPO_CIERRE, WorkDate.parseDateToString(item.getCierre()));
        valores.put(HorarioDataBaseHelper.CAMPO_DIA, item.getDia());

        return db.insert(HorarioDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(HorarioEntity item)
    {
        String[] argumentos = new String[]{String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();

        valores.put(HorarioDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(HorarioDataBaseHelper.CAMPO_DIA, item.getDia());
        valores.put(HorarioDataBaseHelper.CAMPO_APERTURA, WorkDate.parseDateToString(item.getApertura()));
        valores.put(HorarioDataBaseHelper.CAMPO_CIERRE, WorkDate.parseDateToString(item.getCierre()));
        valores.put(HorarioDataBaseHelper.CAMPO_DIA, item.getDia());

        db.update(HorarioDataBaseHelper.TABLE_NAME, valores, HorarioDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }


    public void eliminar(HorarioEntity persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(HorarioDataBaseHelper.TABLE_NAME,
                HorarioDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }


    public Cursor findAll()
    {
        String[] campos = {
                HorarioDataBaseHelper.CAMPO_ID,
                HorarioDataBaseHelper.CAMPO_DIA,
                HorarioDataBaseHelper.CAMPO_APERTURA,
                HorarioDataBaseHelper.CAMPO_CIERRE,
                HorarioDataBaseHelper.CAMPO_OBSERVACIONES

        };
        Cursor resultado = db.query(HorarioDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }


    public Cursor findByDayOfWeek(int dayOfWeek)
    {
        try{
            String[] campos = {
                    HorarioDataBaseHelper.CAMPO_ID,
                    HorarioDataBaseHelper.CAMPO_DIA,
                    HorarioDataBaseHelper.CAMPO_APERTURA,
                    HorarioDataBaseHelper.CAMPO_CIERRE,
                    HorarioDataBaseHelper.CAMPO_OBSERVACIONES

            };
            String[] argumentos = new String[]{String.valueOf(dayOfWeek)};
            String from         = HorarioDataBaseHelper.CAMPO_DIA + " =? ";

            Cursor resultado = db.query(
                    HorarioDataBaseHelper.TABLE_NAME,
                    campos,
                    from,
                    argumentos,
                    null,
                    null,
                    null
            );
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e ){
            Toast.makeText(this.context,"Error HorarioController " + e.getMessage(),Toast.LENGTH_LONG ).show();
            return null;
        }
    }


    public HorarioEntity getByDayOfWeekObject(int dayOfWeek){
        try{
            HorarioEntity registro = new HorarioEntity();
            Cursor c =  findByDayOfWeek(dayOfWeek);
            if (c != null)
            {
                c.moveToFirst();
                registro = new HorarioEntity();
                registro.setId(c.getInt(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_ID)));
                registro.setDia(c.getInt(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_DIA)));
                registro.setApertura(WorkDate.parseStringToTime(c.getString(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_APERTURA))));
                registro.setCierre(WorkDate.parseStringToTime(c.getString(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_CIERRE))));
            }
            return registro;
        }catch(Exception e ){
            Toast.makeText(this.context,"Error HorarioController " + e.getMessage(),Toast.LENGTH_LONG ).show();
            return null;
        }
    }





    public ArrayList<HorarioEntity> parseCursorToListArray(Cursor c )
    {
        try{
            ArrayList<HorarioEntity> lista = new ArrayList<HorarioEntity>();
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
            {
                HorarioEntity registro = new HorarioEntity();
                registro.setId(c.getInt(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_ID)));
                registro.setDia(c.getInt(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_DIA)));
                registro.setApertura(WorkDate.parseStringToDate(c.getString(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_APERTURA))));
                registro.setCierre(WorkDate.parseStringToDate(c.getString(c.getColumnIndex(HorarioDataBaseHelper.CAMPO_CIERRE))));
                lista.add(registro);
            }
            return lista;
        }catch(Exception e ){
                Toast.makeText(this.context,"Error HorarioController " + e.getMessage(),Toast.LENGTH_LONG ).show();
                return null;
        }
    }





    public HorarioEntity buscar(int id)
    {
        HorarioEntity registro = null;
        String[] campos = {
                HorarioDataBaseHelper.CAMPO_ID,
                HorarioDataBaseHelper.CAMPO_DIA,
                HorarioDataBaseHelper.CAMPO_APERTURA,
                HorarioDataBaseHelper.CAMPO_CIERRE,
                HorarioDataBaseHelper.CAMPO_OBSERVACIONES
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(HorarioDataBaseHelper.TABLE_NAME, campos,
                HorarioDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new HorarioEntity();
            registro.setId(resultado.getInt(resultado.getColumnIndex(HorarioDataBaseHelper.CAMPO_ID)));
            registro.setDia(resultado.getInt(resultado.getColumnIndex(HorarioDataBaseHelper.CAMPO_DIA)));
            registro.setApertura(WorkDate.parseStringToDate(resultado.getString(resultado.getColumnIndex(HorarioDataBaseHelper.CAMPO_APERTURA))));
            registro.setCierre(WorkDate.parseStringToDate(resultado.getString(resultado.getColumnIndex(HorarioDataBaseHelper.CAMPO_CIERRE))));

        }
        return registro;

    }
    public void limpiar()
    {

        db.delete(HorarioDataBaseHelper.TABLE_NAME, null, null);

    }
    public void reset(){

    }
    public void beginTransaction()
    {
        if ( db != null )
        {
            db.beginTransaction();
        }
    }
    public void flush()
    {
        if ( db != null )
        {
            db.setTransactionSuccessful();
        }
    }
    public void commit()
    {
        if ( db != null )
        {
            db.endTransaction();
        }
    }
}