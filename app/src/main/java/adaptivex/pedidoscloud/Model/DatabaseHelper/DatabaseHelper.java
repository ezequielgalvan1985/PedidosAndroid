package adaptivex.pedidoscloud.Model.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import adaptivex.pedidoscloud.Config.Configurador;
import adaptivex.pedidoscloud.Model.IModelMethods;

public class DatabaseHelper extends SQLiteOpenHelper implements IModelMethods {

    private String createTable;
    private String dropTable;
    private String nameTable;

    public DatabaseHelper(Context context){
        super(context,
                Configurador.DBName,
                null,
                Configurador.DBVersion);
        setCreateTable(createTable);
        setDropTable(dropTable);
    };



    public static int getDBVersion() {
        return Configurador.DBVersion;
    }

    public static String getDBName() {
        return Configurador.DBName;
    }


    @Override
    public String getCreateTable() {
        Log.println(Log.INFO,"CREATE: ",this.createTable);
        return this.createTable;
    }

    @Override
    public String getDropTable() {
        Log.println(Log.INFO,"DROP: ",this.dropTable);
        return this.dropTable;

    }

    @Override
    public String getNameTable() {
        return this.nameTable;
    }
    public void setNameTable(String nameTable){this.nameTable = nameTable;}

    public void setCreateTable(String createTable) {
        this.createTable = createTable;
    }

    public void setDropTable(String dropTable) {
        this.dropTable= dropTable;
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        Log.println(Log.INFO,"DatabaseHelper: ",getCreateTable());
        db.execSQL(getCreateTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.println(Log.INFO,"DatabaseHelper: ",getDropTable());

        db.execSQL(getDropTable());
        onCreate(db);
    }
}
