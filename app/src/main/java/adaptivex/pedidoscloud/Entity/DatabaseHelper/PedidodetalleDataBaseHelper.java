package adaptivex.pedidoscloud.Entity.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class PedidodetalleDataBaseHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME              = Configurador.DBName;
    public static final String TABLE_NAME           = "pedidodetalles";
    public static final int DB_VERSION              = Configurador.DBVersion;
    public static final String CAMPO_PRODUCTO_ID    = "producto_id";
    public static final String CAMPO_CANTIDAD       = "cantidad";
    public static final String CAMPO_PRECIOUNITARIO = "preciounitario";
    public static final String CAMPO_MONTO          = "monto";
    public static final String CAMPO_ESTADO_ID      = "estado_id";
    //internto para la app
    public static final String CAMPO_ANDROID_ID         = "android_id";
    public static final String CAMPO_PEDIDO_ANDROID_ID  = "pedido_android_id";
    //id del server
    public static final String CAMPO_ID             = "id";
    public static final String CAMPO_PEDIDO_ID      = "pedido_id";


    //HELADERIA
    public static final String CAMPO_NRO_POTE            = "nropote";
    public static final String CAMPO_PROPORCION_HELADO   = "proporcion_helado";
    public static final String CAMPO_MEDIDA_POTE         = "medida_pote";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CAMPO_ID             + " integer null , " +
            CAMPO_PEDIDO_ID      + " integer null ," +
            CAMPO_PRODUCTO_ID    + " integer null ," +
            CAMPO_CANTIDAD       + " real null ," +
            CAMPO_PRECIOUNITARIO + " real null ," +
            CAMPO_MONTO          + " real null ," +
            CAMPO_ESTADO_ID      + " integer null, " +
            CAMPO_ANDROID_ID         + " integer primary key autoincrement not null," +
            CAMPO_PEDIDO_ANDROID_ID  + " integer  null " +
            ")";

    public PedidodetalleDataBaseHelper(Context context)
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
