package adaptivex.pedidoscloud.Entity.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import adaptivex.pedidoscloud.Config.Configurador;

/**
 * Created by ezequiel on 30/05/2016.
 */
public class ProductoDataBaseHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME          = "productos";
    public static final String CAMPO_ID            = "id";
    public static final String CAMPO_NOMBRE        = "nombre";
    public static final String CAMPO_DESCRIPCION   = "descripcion";
    public static final String CAMPO_PRECIO        = "precio";
    public static final String CAMPO_STOCK         = "stock";
    public static final String CAMPO_IMAGEN        = "imagen";
    public static final String CAMPO_IMAGENURL      = "imagenurl";
    public static final String CAMPO_CODIGOEXTERNO  = "codigoexterno";
    public static final String CAMPO_ENABLED        = "enabled";
    public static final String CAMPO_CATEGORIA_ID   = "categoria_id";
    public static final String CAMPO_MARCA_ID       = "marca_id";
    public static final String CAMPO_ISPROMO        = "ispromo";
    public static final String CAMPO_UNIDADMEDIDA_ID = "unidadmedida_id";
    public static final String CAMPO_PRECIOPROMO     = "preciopromo_id";
    public static final String CAMPO_ISFRACCIONADO   = "isfraccionado";



    public static final String JSON_CAMPO_CATEGORIA = "categoria";
    public static final String JSON_CAMPO_CATEGORIA_ID = "id";
    public static final String JSON_CAMPO_MARCA = "marca";
    public static final String JSON_CAMPO_MARCA_ID = "id";


    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            CAMPO_ID            + " integer not null ," +
            CAMPO_NOMBRE        + " text not null," +
            CAMPO_DESCRIPCION   + " text null, " +
            CAMPO_PRECIO        + " decimal (7,2) default 0, " +
            CAMPO_STOCK         + " integer null default 0, " +
            CAMPO_IMAGEN        + " text null default '', " +
            CAMPO_IMAGENURL     + " text null default '', " +
            CAMPO_CODIGOEXTERNO + " text null default '', " +
            CAMPO_CATEGORIA_ID  + " integer not null ," +
            CAMPO_MARCA_ID      + " integer not null ," +
            CAMPO_ENABLED       + " integer null default 0, " +
            CAMPO_ISPROMO       + " integer null default 0, " +
            CAMPO_UNIDADMEDIDA_ID + " integer not null, " +
            CAMPO_PRECIOPROMO     + " decimal (7,2) default 0, " +
            CAMPO_ISFRACCIONADO   + " integer null default 0 " +
            ")";

    public ProductoDataBaseHelper(Context context)
    {
        super(context, Configurador.DBName, null, Configurador.DBVersion);
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
