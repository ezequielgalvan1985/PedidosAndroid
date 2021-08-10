package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.List;

import adaptivex.pedidoscloud.Entity.MarcaEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.MarcaDataBaseHelper;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class MarcaRepository
{
    private Context context;
    private MarcaDataBaseHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues valores;

    private String[] campos = {
            MarcaDataBaseHelper.CAMPO_ID,
            MarcaDataBaseHelper.CAMPO_NOMBRE,
            MarcaDataBaseHelper.CAMPO_DESCRIPCION
            };


    public MarcaRepository(Context context)
    {
        this.valores = new ContentValues();
        this.context = context;
    }

    public MarcaRepository abrir() throws SQLiteException
    {
        dbHelper = new MarcaDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void cerrar()
    {
        if ( db != null )
            db.close();
    }

    private void setValores(MarcaEntity item){
        valores.put(MarcaDataBaseHelper.CAMPO_ID, item.getId());
        valores.put(MarcaDataBaseHelper.CAMPO_NOMBRE, item.getNombre());
        valores.put(MarcaDataBaseHelper.CAMPO_DESCRIPCION, item.getDescripcion());

    }




    /* ======== CRUD ========= */
    public long agregar(MarcaEntity item)
    {
        try{
            setValores(item);
            return db.insert(MarcaDataBaseHelper.TABLE_NAME, null, valores);
        }catch (Exception e){
            Log.e("MarcaRepository",e.getMessage());
            return -1;
        }
    }


    public void modificar(MarcaEntity item)
    {
        String[] argumentos = new String[]
                {String.valueOf(item.getId())};
        setValores(item);
        db.update(MarcaDataBaseHelper.TABLE_NAME, valores,
                MarcaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }

    public void eliminar(MarcaEntity persona)
    {
        String[] argumentos = new String[] {String.valueOf(persona.getId())};
        db.delete(MarcaDataBaseHelper.TABLE_NAME,
                MarcaDataBaseHelper.CAMPO_ID + " = ?", argumentos);
    }

    public Cursor obtenerTodos()
    {
        return findBy(null,null);
    }



    public MarcaEntity findById(int id){
        try{
            String[] argumentos = {String.valueOf(id)};
            String sWhere = MarcaDataBaseHelper.CAMPO_ID + " = ?";
            return parseRecordToMarca(findBy(sWhere,argumentos));
        }catch(Exception e){
            Log.e("MarcaRepo findbyid",e.getMessage());
            return null;
        }
    }

    public MarcaEntity buscar(int id)
    {
        return findById(id);
    }



    public void limpiar()
    {
        db.delete(MarcaDataBaseHelper.TABLE_NAME, null, null);
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
    /* procedimientos de acceso base*/
    public Cursor findBy(String sWhere, String[] argumentos ){
        try{
            Cursor resultado = db.query(MarcaDataBaseHelper.TABLE_NAME, campos,
                    sWhere, argumentos, null, null, null);
            if (resultado != null)
            {
                resultado.moveToFirst();
            }
            return resultado;
        }catch(Exception e){
            Log.e("MarcaRepository findby",e.getMessage());
            return null;
        }
    }
    /* ====== Parseadores de Cursores ========= */
    public List<MarcaEntity> parseCursorToListMarca(Cursor resultado){
        try{
            List<MarcaEntity> marcaEntityList = null;
            if (resultado != null)
            {
                while(resultado.moveToNext()){
                    marcaEntityList.add(parseRecordToMarca(resultado));
                }
            }
            return marcaEntityList;
        }catch(Exception e) {
            Log.e("MarcaRepo parsecursor", e.getMessage());
            return null;
        }
    }

    public MarcaEntity parseRecordToMarca(Cursor resultado){
        try{
            MarcaEntity registro = new MarcaEntity();
            if (resultado != null)
            {
                registro.setId(resultado.getInt(resultado.getColumnIndex(MarcaDataBaseHelper.CAMPO_ID)));
                registro.setNombre(resultado.getString(resultado.getColumnIndex(MarcaDataBaseHelper.CAMPO_NOMBRE)));
                registro.setDescripcion(resultado.getString(resultado.getColumnIndex(MarcaDataBaseHelper.CAMPO_DESCRIPCION)));
            }
            return registro;
        }catch(Exception e) {
            Log.e("MarcaRepo recordtomarca", e.getMessage());
            return null;
        }
    }


}