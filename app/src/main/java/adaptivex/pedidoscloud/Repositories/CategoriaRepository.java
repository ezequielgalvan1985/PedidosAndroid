package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import adaptivex.pedidoscloud.Entity.CategoriaEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.CategoriaDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class CategoriaRepository
{
    private Context context;
    private CategoriaDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public CategoriaRepository(Context context)
    {
        this.context = context;
    }

    public CategoriaRepository abrir() throws SQLiteException
    {
        dbHelper = new CategoriaDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public Integer count(){
        try{
            return abrir().obtenerTodos().getCount();
        }catch(Exception e ){
            Log.d("Clientes:", e.getMessage());
            return null;
        }

    }
    public void cerrar()
    {
        if ( db != null )
        {
            db.close();
        }
    }

    public long agregar(CategoriaEntity item)
    {
        ContentValues valores = new ContentValues();
        valores.put(CategoriaDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(CategoriaDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(CategoriaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());


        return db.insert(CategoriaDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(CategoriaEntity item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();
        valores.put(CategoriaDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(CategoriaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        db.update(CategoriaDataBaseHelper.TABLE_NAME, valores,
                CategoriaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(CategoriaEntity persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(CategoriaDataBaseHelper.TABLE_NAME,
                CategoriaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {CategoriaDataBaseHelper.CAMPO_ID,
                CategoriaDataBaseHelper.CAMPO_NOMBRE,
                CategoriaDataBaseHelper.CAMPO_DESCRIPCION

        };
        Cursor resultado = db.query(CategoriaDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public CategoriaEntity buscar(int id)
    {
        CategoriaEntity registro = null;
        String[] campos = {CategoriaDataBaseHelper.CAMPO_ID,
                CategoriaDataBaseHelper.CAMPO_DESCRIPCION
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(CategoriaDataBaseHelper.TABLE_NAME, campos,
                CategoriaDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new CategoriaEntity();
            registro.setId(resultado.getInt(resultado.getColumnIndex(CategoriaDataBaseHelper.CAMPO_ID)));
            registro.setDescripcion(resultado.getString(resultado.getColumnIndex(CategoriaDataBaseHelper.CAMPO_DESCRIPCION)));

        }
        return registro;

    }
    public void limpiar()
    {
        db.delete(CategoriaDataBaseHelper.TABLE_NAME, null, null);
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