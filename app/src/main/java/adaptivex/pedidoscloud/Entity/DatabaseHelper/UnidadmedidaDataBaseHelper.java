package adaptivex.pedidoscloud.Entity.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class UnidadmedidaDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = Configurador.DBName;
    public static final String TABLE_NAME = "unidadmedidas";
    public static final int DB_VERSION = Configurador.DBVersion ;
    public static final String CAMPO_ID          = "id";
    public static final String CAMPO_NOMBRE      = "nombre";
    public static final String CAMPO_DESCRIPCION = "descripcion";
    public static final String CAMPO_ABREVIATURA = "abreviatura";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID + " integer not null," +
            CAMPO_NOMBRE + " text," +
            CAMPO_ABREVIATURA + " text," +
            CAMPO_DESCRIPCION + " text  )";

    public UnidadmedidaDataBaseHelper(Context context)
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
