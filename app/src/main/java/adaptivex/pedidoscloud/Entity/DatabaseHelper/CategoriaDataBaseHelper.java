package adaptivex.pedidoscloud.Entity.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class CategoriaDataBaseHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME = "categorias";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_DESCRIPCION = "descripcion";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID + " integer not null," +
            CAMPO_NOMBRE + " text , " +
            CAMPO_DESCRIPCION + " text " +
            " )";



    public CategoriaDataBaseHelper(Context context)
    {
        super(context, Configurador.DBName, null, Configurador.DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.println(Log.INFO,"DatabaseHelper: ",CREATE_TABLE);
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.println(Log.INFO,"DatabaseHelper: ",DROP_TABLE);
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}
