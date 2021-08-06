package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import adaptivex.pedidoscloud.Entity.DatabaseHelper.EstadoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.EstadoEntity;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class EstadoRepository
{
    private Context context;
    private EstadoDataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public EstadoRepository(Context context)
    {
        this.context = context;
    }

    public EstadoRepository abrir() throws SQLiteException
    {
        dbHelper = new EstadoDataBaseHelper(context);
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

    public long agregar(EstadoEntity item)
    {
        ContentValues valores = new ContentValues();
        valores.put(EstadoDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(EstadoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(EstadoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());

        return db.insert(EstadoDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(EstadoEntity item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();
        valores.put(EstadoDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(EstadoDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());

        db.update(EstadoDataBaseHelper.TABLE_NAME, valores,
                EstadoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public void eliminar(EstadoEntity persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(EstadoDataBaseHelper.TABLE_NAME,
                EstadoDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {EstadoDataBaseHelper.CAMPO_ID,
                EstadoDataBaseHelper.CAMPO_NOMBRE,
                EstadoDataBaseHelper.CAMPO_DESCRIPCION

        };
        Cursor resultado = db.query(EstadoDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public EstadoEntity buscar(int id)
    {
        EstadoEntity registro = null;
        String[] campos = {EstadoDataBaseHelper.CAMPO_ID,
                EstadoDataBaseHelper.CAMPO_NOMBRE,
                EstadoDataBaseHelper.CAMPO_DESCRIPCION

        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(EstadoDataBaseHelper.TABLE_NAME, campos,
                EstadoDataBaseHelper.CAMPO_ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new EstadoEntity();
            registro.setId(resultado.getInt(resultado.getColumnIndex(EstadoDataBaseHelper.CAMPO_ID)));
            registro.setNombre(resultado.getString(resultado.getColumnIndex(EstadoDataBaseHelper.CAMPO_NOMBRE)));
            registro.setDescripcion(resultado.getString(resultado.getColumnIndex(EstadoDataBaseHelper.CAMPO_DESCRIPCION)));

        }
        return registro;

    }
    public void limpiar()
    {
        db.delete(EstadoDataBaseHelper.TABLE_NAME, null, null);

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