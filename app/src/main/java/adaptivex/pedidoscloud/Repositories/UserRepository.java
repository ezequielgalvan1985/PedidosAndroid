package adaptivex.pedidoscloud.Repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import adaptivex.pedidoscloud.Config.GlobalValues;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.EstadoDataBaseHelper;
import adaptivex.pedidoscloud.Entity.UserEntity;
import adaptivex.pedidoscloud.Entity.DatabaseHelper.UserDataBaseHelper;
import adaptivex.pedidoscloud.Servicios.Helpers.HelperUser;

/**
 * Created by ezequiel on 28/05/2016.
 */
public class UserRepository extends AppController{
    private UserEntity user;
    private String tabla = "User";

    /*      DATABASE        */
    private Context context;
    private UserDataBaseHelper dbHelper;
    private SQLiteDatabase db;




    //Obtener datos del usuario guardado en la base de datos
    public UserEntity getUserDB(int id){
        try{
            UserRepository pc = new UserRepository(this.getContext());
            UserEntity u = pc.abrir().findUser(id);
            return u;
        }catch(Exception e){
            Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return null;
        }
    }








    public boolean add(UserEntity userParam){

        //cargar valores
        ContentValues valores = new ContentValues();
        valores.put("username",userParam.getUsername());
        valores.put("email", userParam.getEmail());
        valores.put("password",userParam.getPassword());

        try {
            this.getConn().add(tabla, valores);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean login(String username, String pass){
        boolean result = false;
        HelperUser hu = new HelperUser(this.getContext());
        //hu.setUser(this.getUser());
        hu.execute();
        return result;
    }

    public boolean isUserLogin(){
        if (GlobalValues.getInstancia().getUsuariologueado()!= null){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }





    public UserRepository(Context context)
    {
        this.context = context;
    }

    public UserRepository abrir() throws SQLiteException
    {
        dbHelper = new UserDataBaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public long addDB(UserEntity item)
    {
        try {
            ContentValues valores = new ContentValues();

            valores.put(UserDataBaseHelper.ID, item.getId());
            valores.put(UserDataBaseHelper.USERNAME, item.getUsername());
            valores.put(UserDataBaseHelper.EMAIL, item.getEmail());
            valores.put(UserDataBaseHelper.LOGUED, item.getLogued());

            return db.insert(UserDataBaseHelper.TABLE_NAME, null, valores);
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return -1;
        }

    }



    public void editDB(UserEntity item)
    {
        try {
            String[] argumentos = new String[] {String.valueOf(item.getId())};

            ContentValues valores = new ContentValues();

            valores.put(UserDataBaseHelper.ID, item.getId());
            valores.put(UserDataBaseHelper.USERNAME, item.getUsername());
            valores.put(UserDataBaseHelper.EMAIL, item.getEmail());
            valores.put(UserDataBaseHelper.LOGUED, item.getLogued());

            db.update(UserDataBaseHelper.TABLE_NAME,
                    valores,
                    UserDataBaseHelper.ID + " = ?",
                    argumentos);
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void limpiar()
    {
        db.delete(EstadoDataBaseHelper.TABLE_NAME, null, null);
    }


    public UserEntity findById(Integer id)
    {
        UserEntity registro = new UserEntity();
        String[] campos = {
                UserDataBaseHelper.ID,
                UserDataBaseHelper.USERNAME,
                UserDataBaseHelper.GROUP_ID,
                UserDataBaseHelper.EMAIL
        };
        String[] argumentos = {String.valueOf(id)};

        Cursor resultado = db.query(UserDataBaseHelper.TABLE_NAME, campos,
                UserDataBaseHelper.ID + " = ?", argumentos, null, null, null);
        if (resultado != null)
        {
            resultado.moveToFirst();
            registro = parseObjectFromRecord(resultado);
        }
        return registro;
    }

    public UserEntity findUser(int id)
    {
        try {
            UserEntity registro = new UserEntity();
            String[] campos = {
                    UserDataBaseHelper.ID,
                    UserDataBaseHelper.USERNAME,
                    UserDataBaseHelper.GROUP_ID,
                    UserDataBaseHelper.EMAIL,
                    UserDataBaseHelper.LOGUED,
            };
            String[] argumentos = new String[]{String.valueOf(id)};

            Cursor resultado = db.query(UserDataBaseHelper.TABLE_NAME, campos,
                    UserDataBaseHelper.ID + " = ?", argumentos, null, null, null);
            if (resultado != null) {
                registro = parseObjectFromRecord(resultado);
            }
            return registro;
        }catch(Exception e){
            return null;
        }

    }



    public UserEntity parseObjectFromRecord(Cursor c ){
        try{
            UserEntity object = new UserEntity();
            if (c!=null){
                    c.moveToFirst();
                    object.setId(c.getInt(c.getColumnIndex(UserDataBaseHelper.ID)));
                    object.setUsername(c.getString(c.getColumnIndex(UserDataBaseHelper.USERNAME)));
                    object.setEmail(c.getString(c.getColumnIndex(UserDataBaseHelper.EMAIL)));
                    object.setLogued(c.getString(c.getColumnIndex(UserDataBaseHelper.LOGUED)));
                }

            return object;
        }catch(Exception e){

            return null;
        }
    }


}
