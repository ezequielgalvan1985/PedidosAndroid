package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.PedidoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.UserProfileDataBaseHelper;
import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Entity.PedidoEntity;
import adaptivex.pedidoscloud.Entity.UserProfileEntity;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class UserProfileRepository
{
    private Context context;
    private UserProfileDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues valores;
    private String[] campos = {UserProfileDataBaseHelper.ID,
            UserProfileDataBaseHelper.NOMBRE,
            UserProfileDataBaseHelper.APELLIDO,
            UserProfileDataBaseHelper.CALLE,
            UserProfileDataBaseHelper.NRO,
            UserProfileDataBaseHelper.PISO,
            UserProfileDataBaseHelper.CONTACTO,
            UserProfileDataBaseHelper.TELEFONO
            };

    public UserProfileRepository(Context context)
    {
        this.context = context;
        this.valores = new ContentValues();
    }

    public UserProfileRepository abrir() throws SQLiteException
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
    public void setValores(UserProfileEntity item) {
        try {
            valores.put(UserProfileDataBaseHelper.ID, item.getId());
            valores.put(UserProfileDataBaseHelper.NOMBRE, item.getNombre());
            valores.put(UserProfileDataBaseHelper.APELLIDO, item.getApellido());
            valores.put(UserProfileDataBaseHelper.CALLE, item.getCalle());
            valores.put(UserProfileDataBaseHelper.NRO, item.getNro());
            valores.put(UserProfileDataBaseHelper.PISO, item.getPiso());
            valores.put(UserProfileDataBaseHelper.CONTACTO, item.getContacto());
            valores.put(UserProfileDataBaseHelper.TELEFONO, item.getTelefono());
        } catch (Exception e) {
            Log.e("UserProfile",e.getMessage());
        }
    }

    /* ============= CRUD ============================ */
    public long agregar(UserProfileEntity item)
    {
        setValores(item);
        return db.insert(UserProfileDataBaseHelper.TABLE_NAME, null, valores);
    }


    public void modificar(UserProfileEntity item)
    {
        String[] argumentos = new String[] {String.valueOf(item.getId())};
        setValores(item);
        db.update(UserProfileDataBaseHelper.TABLE_NAME, valores,
                UserProfileDataBaseHelper.ID + " = ?", argumentos);
    }

    public void eliminar(UserProfileEntity persona)
    {
        String[] argumentos = new String[]
                {String.valueOf(persona.getId())};
        db.delete(UserProfileDataBaseHelper.TABLE_NAME,
                UserProfileDataBaseHelper.ID + " = ?", argumentos);
    }

    public Cursor obtenerTodos()
    {
        Cursor resultado = db.query(UserProfileDataBaseHelper.TABLE_NAME, campos,
                null, null, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
        }
        return resultado;
    }
    public UserProfileEntity findByCurrentUser(){
        try{
            return parseRecordToUserProfile(findBy(null,null));
        }catch(Exception e){
            Log.e("findbycurrentuser",e.getMessage());
            return null;
        }
    }
    /* ========== Busqueda avanzada ================= */
    public UserProfileEntity findById(int id){
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = UserProfileDataBaseHelper.ID + " = ?";
            return parseRecordToUserProfile(findBy(sWhere,argumentos));
        }catch(Exception e){
            Log.e("MarcaRepo",e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve Cursor,
     * @param sWhere string con el nombre de los campos
     * @param argumentos array de string con los valores de cada parametro
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public Cursor findBy(String sWhere, String[] argumentos ){
        try{
            Cursor resultado = db.query(UserProfileDataBaseHelper.TABLE_NAME, campos,
                    sWhere, argumentos, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Log.e("UserPRofileRepository",e.getMessage());
            return null;
        }
    }

    /* ====== Parseadores de Cursores ========= */
    /**
     * Devuelve List de Entity,
     * @param resultado recibe como parametro un cursor
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public List<UserProfileEntity> parseCursorToList(Cursor resultado){
        try{
            List<UserProfileEntity> userProfileEntityList = null;
            if (resultado != null)
            {
                while(resultado.moveToNext()){
                    userProfileEntityList.add(parseRecordToUserProfile(resultado));
                }
            }
            return userProfileEntityList;
        }catch(Exception e) {
            Log.e("UserProfileRepo", e.getMessage());
            return null;
        }
    }

    /**
     * Devuelve UserProfileEntity,
     * @param resultado Recibe como parametro Cursor
     * @author Ezequiel
     * @version 2021.08
     * @since 1.0
     */
    public UserProfileEntity parseRecordToUserProfile(Cursor resultado){
        try{
            UserProfileEntity registro = new UserProfileEntity();
            if (resultado != null)
            {
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
        }catch(Exception e) {
            Log.e("UserProfileRepo", e.getMessage());
            return null;
        }
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