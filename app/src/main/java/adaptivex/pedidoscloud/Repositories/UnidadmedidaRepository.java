package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import adaptivex.pedidoscloud.Entity.DatabaseHelper.UnidadmedidaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.Unidadmedida;

/**
 * Created by ezequiel on 08/2021.
 */
public class UnidadmedidaRepository
{
    private Context context;
    private UnidadmedidaDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    public UnidadmedidaRepository(Context context)
    {
        this.context = context;
    }

    public UnidadmedidaRepository abrir() throws SQLiteException
    {
        dbHelper = new UnidadmedidaDataBaseHelper(context);
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

    public long agregar(Unidadmedida item)
    {
        ContentValues valores = new ContentValues();
        valores.put(UnidadmedidaDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(UnidadmedidaDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(UnidadmedidaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        valores.put(UnidadmedidaDataBaseHelper.CAMPO_ABREVIATURA, item.getAbreviatura());

        return db.insert(UnidadmedidaDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(Unidadmedida item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();

        valores.put(UnidadmedidaDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(UnidadmedidaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());
        valores.put(UnidadmedidaDataBaseHelper.CAMPO_ABREVIATURA, item.getAbreviatura());
        db.update(UnidadmedidaDataBaseHelper.TABLE_NAME, valores,
                UnidadmedidaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(Unidadmedida persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(UnidadmedidaDataBaseHelper.TABLE_NAME,
                UnidadmedidaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {UnidadmedidaDataBaseHelper.CAMPO_ID,
                UnidadmedidaDataBaseHelper.CAMPO_NOMBRE,
                UnidadmedidaDataBaseHelper.CAMPO_DESCRIPCION,
                UnidadmedidaDataBaseHelper.CAMPO_ABREVIATURA

        };
        Cursor resultado = db.query(UnidadmedidaDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public Unidadmedida buscar(int id)
    {
        Unidadmedida registro = null;
        String[] campos = {UnidadmedidaDataBaseHelper.CAMPO_ID,
                UnidadmedidaDataBaseHelper.CAMPO_NOMBRE,
                UnidadmedidaDataBaseHelper.CAMPO_DESCRIPCION,
                UnidadmedidaDataBaseHelper.CAMPO_ABREVIATURA
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(UnidadmedidaDataBaseHelper.TABLE_NAME, campos,
                UnidadmedidaDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new Unidadmedida();
            registro.setId(resultado.getInt(resultado.getColumnIndex(UnidadmedidaDataBaseHelper.CAMPO_ID)));
            registro.setNombre(resultado.getString(resultado.getColumnIndex(UnidadmedidaDataBaseHelper.CAMPO_NOMBRE)));
            registro.setDescripcion(resultado.getString(resultado.getColumnIndex(UnidadmedidaDataBaseHelper.CAMPO_DESCRIPCION)));
            registro.setAbreviatura(resultado.getString(resultado.getColumnIndex(UnidadmedidaDataBaseHelper.CAMPO_ABREVIATURA)));
        }
        return registro;

    }
    public void limpiar()
    {

        db.delete(UnidadmedidaDataBaseHelper.TABLE_NAME, null, null);

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