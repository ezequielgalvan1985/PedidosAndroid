package adaptivex.pedidoscloud.Model.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ClienteDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "clientes";
    public static final int DB_VERSION = Configurador.DBVersion ;
    public static final String CAMPO_ID = "id";

    public static final String CAMPO_RAZONSOCIAL = "razonsocial";
    public static final String CAMPO_DIRECCION = "direccion";
    public static final String CAMPO_TELEFONO = "telefono";
    public static final String CAMPO_EMAIL = "email";
    public static final String CAMPO_CONTACTO = "contacto";
    public static final String CAMPO_NDOC = "ndoc";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            CAMPO_ID + " integer not null," +
            CAMPO_RAZONSOCIAL + " text  ," +
            CAMPO_DIRECCION + " text  ," +
            CAMPO_TELEFONO + " text  ," +
            CAMPO_CONTACTO + " text , " +
            CAMPO_EMAIL + " text , " +
            CAMPO_NDOC + " text  " +
            ")";

    public ClienteDataBaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

}
