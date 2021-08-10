package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Entity.ParameterEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.ParameterDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ParameterRepository
{
    private Context context;
    private ParameterDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private String[] campos = {
            ParameterDataBaseHelper.ID,
            ParameterDataBaseHelper.DESCRIPCION,
            ParameterDataBaseHelper.NOMBRE,
            ParameterDataBaseHelper.VALOR_INTEGER,
            ParameterDataBaseHelper.VALOR_DECIMAL,
            ParameterDataBaseHelper.VALOR_TEXTO
    };
    private ContentValues valores = new ContentValues();


    public ParameterRepository(Context context)
    {
        this.context = context;
    }

    public ParameterRepository abrir() throws SQLiteException
    {
        dbHelper = new ParameterDataBaseHelper(context);
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

    public long agregar(ParameterEntity item)
    {
        setValores(item);
        return db.insert(ParameterDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(ParameterEntity item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        setValores(item);
        db.update(ParameterDataBaseHelper.TABLE_NAME, valores,
                ParameterDataBaseHelper.ID + " = ?", argumentos);
    }
    public void eliminar(ParameterEntity persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(ParameterDataBaseHelper.TABLE_NAME,
                ParameterDataBaseHelper.ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {
                ParameterDataBaseHelper.ID,
                ParameterDataBaseHelper.DESCRIPCION,
                ParameterDataBaseHelper.NOMBRE,
                ParameterDataBaseHelper.VALOR_INTEGER,
                ParameterDataBaseHelper.VALOR_DECIMAL,
                ParameterDataBaseHelper.VALOR_TEXTO
                //      ParameterDataBaseHelper.VALOR_FECHA
        };
        Cursor resultado = db.query(ParameterDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public ParameterEntity findById(int id)
    {
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = ParameterDataBaseHelper.ID + " = ?";
            return parseRecordToParameter(findBy(sWhere,argumentos));
        }catch(Exception e){
            Log.e("ParameterRepo",e.getMessage());
            return null;
        }
    }



    public void setValores(ParameterEntity item){
        valores.put(ParameterDataBaseHelper.NOMBRE, item.getNombre());
        valores.put(ParameterDataBaseHelper.DESCRIPCION, item.getDescripcion());
        valores.put(ParameterDataBaseHelper.VALOR_TEXTO, item.getValor_texto());
        valores.put(ParameterDataBaseHelper.VALOR_INTEGER, item.getValor_integer());
        valores.put(ParameterDataBaseHelper.VALOR_DECIMAL, item.getValor_decimal());
    }

    public ParameterEntity findByNombre(String pnombre){
        try{
            String[] argumentos = {pnombre};
            String sWhere = ParameterDataBaseHelper.NOMBRE + " = ?";
            return parseRecordToParameter(findBy(sWhere, argumentos));
        }catch (Exception e ){
            Log.e("ParameterRepository", e.getMessage());
            return null;
        }
    }
    /* procedimientos de acceso base*/
    public Cursor findBy(String sWhere, String[] argumentos ){
        try{
            Cursor resultado = db.query(ParameterDataBaseHelper.TABLE_NAME, campos,
                    sWhere, argumentos, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Log.e("ParameterRepository",e.getMessage());
            return null;
        }
    }

    /* ====== Parseadores de Cursores ========= */
    public List<ParameterEntity> parseCursorToListParameter(Cursor resultado){
        try{
            List<ParameterEntity> parameterEntityList = null;
            if (resultado != null)
            {
                while(resultado.moveToNext()){
                    parameterEntityList.add(parseRecordToParameter(resultado));
                }
            }
            return parameterEntityList;
        }catch(Exception e) {
            Log.e("ParameterRepo", e.getMessage());
            return null;
        }
    }

    public ParameterEntity parseRecordToParameter(Cursor resultado){
        try{
            ParameterEntity registro = new ParameterEntity();
            if (resultado != null)
            {
                registro.setId(resultado.getInt(resultado.getColumnIndex(ParameterDataBaseHelper.ID)));
                registro.setNombre(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.NOMBRE)));
                registro.setDescripcion(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.DESCRIPCION)));
                registro.setValor_texto(resultado.getString(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_TEXTO)));
                registro.setValor_decimal(resultado.getDouble(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_DECIMAL)));
                registro.setValor_integer(resultado.getInt(resultado.getColumnIndex(ParameterDataBaseHelper.VALOR_INTEGER)));
            }
            return registro;
        }catch(Exception e) {
            Log.e("MarcaRepo", e.getMessage());
            return null;
        }
    }

    public void limpiar()
    {
        db.delete(ParameterDataBaseHelper.TABLE_NAME, null, null);
    }
    public void beginTransaction()
    {
        if ( db != null )
            db.beginTransaction();
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