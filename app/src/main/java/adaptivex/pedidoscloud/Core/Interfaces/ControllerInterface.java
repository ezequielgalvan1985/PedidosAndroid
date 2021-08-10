package adaptivex.pedidoscloud.Core.Interfaces;

import android.database.Cursor;

/**
 * Created by egalvan on 22/3/2018.
 */

public interface ControllerInterface {

    public long add(Object object);
    public boolean edit(Object object);
    public boolean delete(Object object);
    public void limpiar();
    public void beginTransaction();
    public void flush();
    public void commit();


    public Cursor findBy(String sWhere, String[] argumentos);




}
