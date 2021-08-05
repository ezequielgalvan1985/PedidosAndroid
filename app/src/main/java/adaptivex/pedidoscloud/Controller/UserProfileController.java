package adaptivex.pedidoscloud.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import adaptivex.pedidoscloud.Model.DatabaseHelper.UserProfileDataBaseHelper;
import adaptivex.pedidoscloud.Model.UserProfile;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class UserProfileController
{
    private Context context;
    private UserProfileDataBaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserProfileController(Context context)
    {
        this.context = context;
    }

    public UserProfileController abrir() throws SQLiteException
    {
        dbHelper = new UserProfileDataBaseHelper(context);
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

    public long agregar(UserProfile item)
    {
        ContentValues valores = new ContentValues();
        valores.put(UserProfileDataBaseHelper.ID, item.getId());
        valores.put(UserProfileDataBaseHelper.NOMBRE, item.getNombre());
        valores.put(UserProfileDataBaseHelper.APELLIDO, item.getApellido());
        valores.put(UserProfileDataBaseHelper.CALLE, item.getCalle());
        valores.put(UserProfileDataBaseHelper.NRO, item.getNro());
        valores.put(UserProfileDataBaseHelper.PISO, item.getPiso());
        valores.put(UserProfileDataBaseHelper.CONTACTO, item.getContacto());
        valores.put(UserProfileDataBaseHelper.TELEFONO, item.getTelefono());

        return db.insert(UserProfileDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(UserProfile item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        ContentValues valores = new ContentValues();

        valores.put(UserProfileDataBaseHelper.NOMBRE, item.getNombre());
        valores.put(UserProfileDataBaseHelper.APELLIDO, item.getApellido());
        valores.put(UserProfileDataBaseHelper.CALLE, item.getCalle());
        valores.put(UserProfileDataBaseHelper.NRO, item.getNro());
        valores.put(UserProfileDataBaseHelper.PISO, item.getPiso());
        valores.put(UserProfileDataBaseHelper.CONTACTO, item.getContacto());
        valores.put(UserProfileDataBaseHelper.TELEFONO, item.getTelefono());

        db.update(UserProfileDataBaseHelper.TABLE_NAME, valores,
                UserProfileDataBaseHelper.ID + " = ?", argumentos);
    }
    public void eliminar(UserProfile persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(UserProfileDataBaseHelper.TABLE_NAME,
                UserProfileDataBaseHelper.ID + " = ?", argumentos);
    }
    public Cursor obtenerTodos()
    {
        String[] campos = {UserProfileDataBaseHelper.ID,
                            UserProfileDataBaseHelper.NOMBRE,
                            UserProfileDataBaseHelper.APELLIDO,
                            UserProfileDataBaseHelper.CALLE,
                            UserProfileDataBaseHelper.NRO,
                            UserProfileDataBaseHelper.PISO,
                            UserProfileDataBaseHelper.CONTACTO,
                            UserProfileDataBaseHelper.TELEFONO
        };
        Cursor resultado = db.query(UserProfileDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }

    public UserProfile buscar(int id)
    {
        UserProfile registro = null;
        String[] campos = {
                UserProfileDataBaseHelper.ID,
                UserProfileDataBaseHelper.NOMBRE,
                UserProfileDataBaseHelper.APELLIDO,
                UserProfileDataBaseHelper.CALLE,
                UserProfileDataBaseHelper.NRO,
                UserProfileDataBaseHelper.PISO,
                UserProfileDataBaseHelper.CONTACTO,
                UserProfileDataBaseHelper.TELEFONO
        };
        String[] argumentos = {String.valueOf(id)};
        Cursor resultado = db.query(UserProfileDataBaseHelper.TABLE_NAME, campos,
                UserProfileDataBaseHelper.ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = new UserProfile();
            registro.setId(resultado.getInt(resultado.getColumnIndex(UserProfileDataBaseHelper.ID)));
            registro.setNombre(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.NOMBRE)));
            registro.setApellido(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.APELLIDO)));
            registro.setCalle(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.CALLE)));
            registro.setNro(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.NRO)));
            registro.setPiso(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.PISO)));
            registro.setContacto(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.CONTACTO)));
            registro.setTelefono(resultado.getString(resultado.getColumnIndex(UserProfileDataBaseHelper.TELEFONO)));

        }
        return registro;

    }
    public void limpiar()
    {

        db.delete(UserProfileDataBaseHelper.TABLE_NAME, null, null);

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