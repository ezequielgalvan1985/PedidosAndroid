package adaptivex.pedidoscloud.Entity.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class UserDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "users";
    public static final int    DB_VERSION = Configurador.DBVersion ;

    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
    public static final String GROUP_ID = "group_id";
    public static final String LOGUED    = "logued";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            ID + "         integer  null," +
            USERNAME + "   text null, " +
            GROUP_ID + "   text null, " +
            EMAIL + "      text null, " +
            LOGUED    + "  text null " +
            " )";

    public UserDataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
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
